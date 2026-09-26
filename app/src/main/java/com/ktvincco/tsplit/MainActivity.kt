package com.ktvincco.tsplit

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.ktvincco.tsplit.data.AndroidDatabase
import com.ktvincco.tsplit.data.AndroidEnvironmentConnector
import com.ktvincco.tsplit.data.AndroidLogger
import com.ktvincco.tsplit.data.AndroidPermissionController
import android.widget.ImageView
import kotlin.collections.mutableListOf
import android.media.AudioAttributes
import android.media.SoundPool
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Switch
import java.io.IOException
import kotlin.collections.set
import androidx.core.content.edit
import com.ktvincco.tsplit.data.Surface2D
import com.ktvincco.tsplit.data.surface2DToAndroidBitmap
import com.ktvincco.tsplit.domain.KeyboardInput
import com.ktvincco.tsplit.domain.KeyboardService


class MainActivity : ComponentActivity() {

    private val PREFS_NAME = "keyboard_prefs"
    private val KEY_BOTTOM_LINE_ENABLED = "bottom_line_enabled"

    // Create platform components
    private val androidLogger = AndroidLogger()
    private val permissionController = AndroidPermissionController(this)
    private val androidDatabase = AndroidDatabase(this, AppInfo.NAME)
    private val environmentConnector = AndroidEnvironmentConnector(this)
    private var keyboardService: KeyboardService? = null

    var keyboardView: View? = null
    var keyboardImageView: ImageView? = null
    var bottomLineSwitch: Switch? = null

    // Sound
    private var soundPool: SoundPool? = null
    private var soundMap = mutableMapOf<String, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        // Create view
        keyboardView = layoutInflater.inflate(R.layout.app_keyboard_test_layout, null)

        // Bottom line switching
        bottomLineSwitch = keyboardView?.findViewById(R.id.bottomLineSwitch)
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val isBottomLineEnabled = prefs.getBoolean(KEY_BOTTOM_LINE_ENABLED, true)
        bottomLineSwitch?.isChecked = isBottomLineEnabled
        bottomLineSwitch?.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit {
                putBoolean(KEY_BOTTOM_LINE_ENABLED, isChecked)
            }
            updateBottomLine()
        }
        updateBottomLine()

        // Assign callbacks to the buttons
        assignButtonCallbacks()

        // Sound
        initializeSoundPlayer()

        // Set view
        setContentView(keyboardView)
    }


    fun updateBottomLine() {
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val isEnabled = prefs.getBoolean(KEY_BOTTOM_LINE_ENABLED, true)

        val displayMetrics = resources.displayMetrics
        val screenHeight = displayMetrics.heightPixels
        val panelHeight = (screenHeight * 0.052f).toInt()

        val bottomLine = keyboardView?.findViewById<ImageView>(R.id.bottomLine)

        bottomLine?.layoutParams = bottomLine.layoutParams?.apply {
            height = if (isEnabled) panelHeight else 0
        }

        bottomLine?.requestLayout()
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