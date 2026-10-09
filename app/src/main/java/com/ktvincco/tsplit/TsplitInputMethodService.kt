package com.ktvincco.tsplit


import android.annotation.SuppressLint
import android.inputmethodservice.InputMethodService
import android.media.AudioAttributes
import android.media.SoundPool
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.ExtractedTextRequest
import android.widget.ImageView
import android.content.res.Configuration
import com.ktvincco.tsplit.data.AndroidDatabase
import com.ktvincco.tsplit.data.AndroidEnvironmentConnector
import com.ktvincco.tsplit.data.AndroidLogger
import com.ktvincco.tsplit.data.AndroidPermissionController
import com.ktvincco.tsplit.data.Surface2D
import com.ktvincco.tsplit.data.surface2DToAndroidBitmap
import com.ktvincco.tsplit.domain.KeyboardInput
import com.ktvincco.tsplit.domain.KeyboardService
import java.io.IOException


class TsplitInputMethodService : InputMethodService() {

    private val androidLogger = AndroidLogger()
    private val permissionController = AndroidPermissionController(null)
    private val androidDatabase = AndroidDatabase(null, AppInfo.NAME)
    private val environmentConnector = AndroidEnvironmentConnector(null)
    private var keyboardService: KeyboardService? = null

    private var keyboardView: View? = null
    private var keyboardImageView: ImageView? = null

    // Sound
    private var soundPool: SoundPool? = null
    private var soundMap = mutableMapOf<String, Int>()

    override fun onCreateInputView(): View {

        // Create view according to orientation
        val orientation = resources.configuration.orientation
        keyboardView = if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutInflater.inflate(R.layout.keyboard_layout_landscape, null)
        } else {
            layoutInflater.inflate(R.layout.keyboard_layout, null)
        }

        // Apply saved keyboard height and bottom line height
        KeyboardSettings.applyToKeyboardView(this, keyboardView)

        // Sound
        initializeSoundPlayer()

        // Log
        Log.i("TsplitInputMethodService", "ON CREATE")

        // Return view
        return keyboardView!!
    }


    override fun onWindowShown() {
        super.onWindowShown()
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
        // Log
        androidLogger.log("Sound", "initializeSoundPlayer()")
    }

    fun onNewFrame(surface2D: Surface2D) {
        keyboardImageView?.setImageBitmap(surface2DToAndroidBitmap(surface2D, this))
    }

    fun emitInput(input: KeyboardInput) {
        // Get current input connection (the active text field)
        val ic = currentInputConnection ?: return

        // Text

        if (input.inputText != null) {
            ic.commitText(input.inputText, 1)
            ic.finishComposingText()
        }

        // Actions

        if (input.actions?.contains("deleteCharacterFromTheLeft") == true) {
            ic.deleteSurroundingText(1, 0)
        }

        if (input.actions?.contains("enter") == true) {
            val actionId = currentInputEditorInfo?.imeOptions ?: EditorInfo.IME_NULL
            when (actionId and EditorInfo.IME_MASK_ACTION) {
                EditorInfo.IME_ACTION_SEARCH -> ic.performEditorAction(EditorInfo.IME_ACTION_SEARCH)
                EditorInfo.IME_ACTION_GO -> ic.performEditorAction(EditorInfo.IME_ACTION_GO)
                EditorInfo.IME_ACTION_SEND -> ic.performEditorAction(EditorInfo.IME_ACTION_SEND)
                EditorInfo.IME_ACTION_DONE -> ic.performEditorAction(EditorInfo.IME_ACTION_DONE)
                else -> ic.commitText("\n", 1)
            }
        }

        if (input.actions?.contains("moveCursorHorizontally") == true) {
            val amount = input.amount ?: 0
            val keyCode = if (amount > 0) KeyEvent.KEYCODE_DPAD_RIGHT else KeyEvent.KEYCODE_DPAD_LEFT

            repeat(kotlin.math.abs(amount).coerceAtMost(50)) {
                ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, keyCode))
                ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, keyCode))
            }
        }

        if (input.actions?.contains("moveCursorVertically") == true) {
            val lines = -(input.amount ?: 0)
            val keyCode = if (lines > 0) KeyEvent.KEYCODE_DPAD_DOWN else KeyEvent.KEYCODE_DPAD_UP

            repeat(kotlin.math.abs(lines).coerceAtMost(50)) {
                ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, keyCode))
                ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, keyCode))
            }
        }

        if (input.actions?.contains("moveSelectionLeft") == true) {
            val extracted = ic.getExtractedText(ExtractedTextRequest(), 0) ?: return
            val start = extracted.selectionStart
            val end = extracted.selectionEnd

            // Move selection start one char to the left
            val newStart = (start - (input.amount ?: 0)).coerceAtLeast(0).coerceAtMost(end)
            ic.setSelection(newStart, end)
        }

        if (input.actions?.contains("deleteSelection") == true) {
            val extracted = ic.getExtractedText(ExtractedTextRequest(), 0) ?: return
            val start = extracted.selectionStart
            val end = extracted.selectionEnd
            if (start != end) {
                ic.setSelection(start, end)
                ic.commitText("", 1)
            }
        }
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

    fun getKeyboardImageViewSize(): Pair<Int, Int> {
        return Pair(keyboardImageView?.width ?: 0, keyboardImageView?.height ?: 0)
    }

    override fun onStartInputView(info: EditorInfo?, restarting: Boolean) {
        super.onStartInputView(info, restarting)
        keyboardImageView = keyboardView?.findViewById<ImageView>(R.id.imageView1)

        // Re-apply in case the settings were changed in the app
        KeyboardSettings.applyToKeyboardView(this, keyboardView)

        assignListeners()
        if (keyboardService == null) {
            keyboardService = KeyboardService(
                { emitInput(it) },
                { playSound(it) },
                { getKeyboardImageViewSize() },
                { getTouchesList() }, { onNewFrame(it) },
                androidLogger, permissionController,
                androidDatabase, environmentConnector
            )
        }
        keyboardService?.start()
    }

    private val touches: MutableList<MutableMap<String, String>> = mutableListOf()

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
    }

    fun getTouchesList(): List<Map<String, String>> {
        return touches
    }

    fun stop() {
        keyboardService?.stop()
        touches.clear()
        resetSoundPlayer()
    }

    override fun onFinishInputView(finishingInput: Boolean) {
        super.onFinishInputView(finishingInput)
        stop()
    }

    private fun resetSoundPlayer() {
        soundPool?.release()
        soundPool = null
        soundMap = mutableMapOf<String, Int>()
    }

    override fun onDestroy() {
        super.onDestroy()
        stop()
    }
}