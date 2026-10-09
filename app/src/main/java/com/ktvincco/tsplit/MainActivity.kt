package com.ktvincco.tsplit

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Switch
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.ktvincco.tsplit.data.AndroidDatabase
import com.ktvincco.tsplit.data.AndroidEnvironmentConnector
import com.ktvincco.tsplit.data.AndroidLogger
import com.ktvincco.tsplit.data.AndroidPermissionController
import com.ktvincco.tsplit.data.Surface2D
import com.ktvincco.tsplit.data.surface2DToAndroidBitmap
import com.ktvincco.tsplit.domain.KeyboardInput
import com.ktvincco.tsplit.domain.KeyboardService
import java.io.IOException


class MainActivity : ComponentActivity() {

    // Step used by the "-" and "+" buttons (dp)
    private val STEP_DP = 10

    // Create platform components
    private val androidLogger = AndroidLogger()
    private val permissionController = AndroidPermissionController(this)
    private val androidDatabase = AndroidDatabase(this, AppInfo.NAME)
    private val environmentConnector = AndroidEnvironmentConnector(this)
    private var keyboardService: KeyboardService? = null

    var keyboardView: View? = null
    var keyboardImageView: ImageView? = null

    // Sound
    private var soundPool: SoundPool? = null
    private var soundMap = mutableMapOf<String, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        // Create view
        keyboardView = layoutInflater.inflate(R.layout.app_keyboard_test_layout, null)

        // Settings controls
        setupBottomLineSwitch()
        bindDpControl(
            Setting.BOTTOM_LINE_HEIGHT,
            R.id.bottomLineHeightInput,
            R.id.minusBottomLineButton,
            R.id.plusBottomLineButton,
            R.id.setBottomLineButton
        )
        bindDpControl(
            Setting.PORTRAIT_HEIGHT,
            R.id.keyboardHeightInput,
            R.id.minusKeyboardHeightButton,
            R.id.plusKeyboardHeightButton,
            R.id.setKeyboardHeightButton
        )
        bindDpControl(
            Setting.LANDSCAPE_HEIGHT,
            R.id.keyboardHeightLandscapeInput,
            R.id.minusKeyboardHeightLandscapeButton,
            R.id.plusKeyboardHeightLandscapeButton,
            R.id.setKeyboardHeightLandscapeButton
        )

        // Apply saved sizes to the dummy keyboard
        KeyboardSettings.applyToKeyboardView(this, keyboardView)

        // Assign callbacks to the buttons
        assignButtonCallbacks()

        // Sound
        initializeSoundPlayer()

        // Set view
        setContentView(keyboardView)
    }


    private fun setupBottomLineSwitch() {
        val bottomLineSwitch = keyboardView?.findViewById<Switch>(R.id.bottomLineSwitch)
        bottomLineSwitch?.isChecked = KeyboardSettings.isBottomLineEnabled(this)
        bottomLineSwitch?.setOnCheckedChangeListener { _, isChecked ->
            KeyboardSettings.setBottomLineEnabled(this, isChecked)
            KeyboardSettings.applyToKeyboardView(this, keyboardView)
        }
    }


    /** Connects one "input + minus + plus + set" row to a setting. */
    private fun bindDpControl(
        setting: Setting, inputId: Int, minusId: Int, plusId: Int, setId: Int
    ) {
        val root = keyboardView ?: return
        val input = root.findViewById<EditText>(inputId)

        input.setText(KeyboardSettings.get(this, setting).toString())

        root.findViewById<Button>(setId).setOnClickListener {
            saveValue(setting, input, readInput(setting, input))
        }
        root.findViewById<Button>(minusId).setOnClickListener {
            saveValue(setting, input, readInput(setting, input) - STEP_DP)
        }
        root.findViewById<Button>(plusId).setOnClickListener {
            saveValue(setting, input, readInput(setting, input) + STEP_DP)
        }
    }


    /** Valid value from the field, otherwise the default (300 for heights). */
    private fun readInput(setting: Setting, input: EditText): Int {
        return KeyboardSettings.parse(setting, input.text?.toString())
    }


    private fun saveValue(setting: Setting, input: EditText, dp: Int) {
        val saved = KeyboardSettings.set(this, setting, dp)
        input.setText(saved.toString())
        input.setSelection(input.text?.length ?: 0)
        KeyboardSettings.applyToKeyboardView(this, keyboardView)
    }


    fun assignButtonCallbacks() {

        // Get buttons
        val openInputSettingsButton = keyboardView?.findViewById<Button>(R.id.open_input_settings)
        val openInputMethodSelector = keyboardView?.findViewById<Button>(R.id.select_input_method)

        // Assign callbacks
        openInputSettingsButton?.setOnClickListener {
            // Open Android input method settings
            val intent = Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        openInputMethodSelector?.setOnClickListener {
            // Show system input method picker
            val imeManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imeManager.showInputMethodPicker()
        }
    }


    fun getKeyboardImageViewSize(): Pair<Int, Int> {
        return Pair(keyboardImageView!!.width, keyboardImageView!!.height)
    }

    override fun onResume() {
        super.onResume()
        keyboardImageView = keyboardView?.findViewById<ImageView>(R.id.imageView1)
        KeyboardSettings.applyToKeyboardView(this, keyboardView)
        assignListeners()
        Log.i("MyKeyboardService", "ON UI " + "${keyboardImageView!!.width}")
        keyboardService = KeyboardService(
            { emitInput(it) },
            { playSound(it) },
            { getKeyboardImageViewSize() },
            { getTouchesList() }, { onNewFrame(it) },
            androidLogger, permissionController, androidDatabase, environmentConnector
        )
        keyboardService?.start()

        // Sound
        initializeSoundPlayer()
    }


    fun initializeSoundPlayer() {
        // Initialize SoundPool for short overlapping sounds
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder()
            .setMaxStreams(64) // allow overlapping sounds
            .setAudioAttributes(audioAttributes)
            .build()
    }

    fun emitInput(input: KeyboardInput) {
        // Empty
    }

    fun playSound(soundResource: String) {

        if (soundPool == null) {
            initializeSoundPlayer()
        }

        // If sound already loaded, play it immediately
        val soundId = soundMap[soundResource]
        if (soundId != null) {
            soundPool?.play(soundId, 1f, 1f, 0, 0, 1f)
            return
        }

        // Otherwise load it from assets
        try {
            assets.openFd(soundResource).use { afd ->
                val newSoundId = soundPool?.load(afd, 1)
                if (newSoundId != null) {
                    soundMap[soundResource] = newSoundId
                }

                // Play as soon as it finishes loading
                soundPool?.setOnLoadCompleteListener { sp, sampleId, status ->
                    if (status == 0 && sampleId == newSoundId) {
                        sp.play(sampleId, 1f, 1f, 0, 0, 1f)
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    val touches: MutableList<MutableMap<String, String>> = mutableListOf() // (x, y)

    @SuppressLint("ClickableViewAccessibility")
    fun assignListeners() {
        keyboardImageView?.setOnTouchListener { v, event ->

            touches.clear()
            val pointerCount = event.pointerCount

            for (i in 0 until pointerCount) {
                val pointerId = event.getPointerId(i)
                val x = event.getX(i)
                val y = event.getY(i)

                val action = event.actionMasked
                val actionIndex = event.actionIndex

                // Skip if this specific pointer has lifted up
                if ((action == MotionEvent.ACTION_UP) ||
                    (action == MotionEvent.ACTION_POINTER_UP && i == actionIndex)) {
                    continue
                }

                touches.add(
                    mutableMapOf(
                        "id" to pointerId.toString(),
                        "x" to x.toString(),
                        "y" to y.toString()
                    )
                )
            }

            true
        }

        keyboardImageView?.setOnClickListener { /* no-op */ }
    }

    fun getTouchesList(): List<Map<String, String>> {
        return touches
    }

    fun onNewFrame(surface2D: Surface2D) {
        keyboardImageView?.setImageBitmap(surface2DToAndroidBitmap(surface2D, this))
    }

    fun stop() {
        keyboardService?.stop()
        touches.clear()
        resetSoundPlayer()
    }

    private fun resetSoundPlayer() {
        soundPool?.release()
        soundPool = null
        soundMap = mutableMapOf<String, Int>()
    }

    override fun onPause() {
        super.onPause()
        stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        stop()
    }

    // Permissions request callback
    @Deprecated("Deprecated in Java (Today don't have a Kotlin solution)")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        @Suppress("DEPRECATION")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // Process in domainController
        permissionController.requestPermissionsResultCallback(
            requestCode, permissions, grantResults)
    }
}