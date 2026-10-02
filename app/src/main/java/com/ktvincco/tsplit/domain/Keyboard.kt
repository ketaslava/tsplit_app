package com.ktvincco.tsplit.domain

import android.graphics.Color
import android.util.Log
import com.ktvincco.tsplit.data.Circle
import com.ktvincco.tsplit.data.Rectangle
import com.ktvincco.tsplit.data.Surface2D
import com.ktvincco.tsplit.data.Graphics
import com.ktvincco.tsplit.data.InputState
import com.ktvincco.tsplit.data.Inputkey
import com.ktvincco.tsplit.data.Logger
import com.ktvincco.tsplit.data.Pointer
import com.ktvincco.tsplit.data.Stack
import com.ktvincco.tsplit.data.TouchProcessor
import com.ktvincco.tsplit.data.keyboardStack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.round
import kotlin.math.sqrt
import kotlin.time.Duration.Companion.milliseconds


class KeyboardInput(val inputText: String? = null, val actions: List<String>? = null,
                    val amount: Int? = null)


class SwitchState(
    val name: String,
    val packsToAdd: List<String> = listOf(),
    val packsToOmit: List<String> = listOf()
)


var switchStates = listOf<SwitchState>(
    SwitchState("latin",
        packsToAdd = listOf("latin", "additionalPunctuation")
    ),
    SwitchState("latinShifted",
        packsToAdd = listOf("latinShifted"),
        packsToOmit = listOf("latin")
    ),
    SwitchState("cyrillic",
        packsToAdd = listOf("cyrillic", "additionalPunctuation")
    ),
    SwitchState("cyrillicShifted",
        packsToAdd = listOf("cyrillicShifted"),
        packsToOmit = listOf("cyrillic")
    ),
    SwitchState("math",
        packsToAdd = listOf("math"),
    ),
    SwitchState("math2",
        packsToAdd = listOf("math2"),
        packsToOmit = listOf("math"),
    ),
    SwitchState("brackets",
        packsToAdd = listOf("brackets"),
    ),
    SwitchState("brackets2",
        packsToAdd = listOf("brackets2"),
        packsToOmit = listOf("brackets"),
    ),
    SwitchState("brackets3",
        packsToAdd = listOf("brackets3"),
        packsToOmit = listOf("brackets"),
    ),
    SwitchState("symbols",
        packsToAdd = listOf("symbols"),
    ),
    SwitchState("symbols2",
        packsToAdd = listOf("symbols2"),
        packsToOmit = listOf("symbols"),
    ),
    SwitchState("symbols3",
        packsToAdd = listOf("symbols3"),
        packsToOmit = listOf("symbols"),
    ),
    SwitchState("symbols4",
        packsToAdd = listOf("symbols4"),
        packsToOmit = listOf("symbols"),
    ),
    SwitchState("punctuation",
        packsToAdd = listOf("punctuation"),
    ),
    SwitchState("punctuation2",
        packsToAdd = listOf("punctuation2"),
        packsToOmit = listOf("punctuation"),
    ),
    SwitchState("letters",
        packsToAdd = listOf("letters"),
    ),
    SwitchState("letters2",
        packsToAdd = listOf("letters2"),
        packsToOmit = listOf("letters"),
    ),
    SwitchState("vowels",
        packsToAdd = listOf("vowels"),
    ),
    SwitchState("variantsOfA",
        packsToAdd = listOf("variantsOfA"),
        packsToOmit = listOf("vowels"),
    ),
    SwitchState("variantsOfE",
        packsToAdd = listOf("variantsOfE"),
        packsToOmit = listOf("vowels"),
    ),
    SwitchState("variantsOfI",
        packsToAdd = listOf("variantsOfI"),
        packsToOmit = listOf("vowels"),
    ),
    SwitchState("variantsOfO",
        packsToAdd = listOf("variantsOfO"),
        packsToOmit = listOf("vowels"),
    ),
    SwitchState("variantsOfU",
        packsToAdd = listOf("variantsOfU"),
        packsToOmit = listOf("vowels"),
    ),
    SwitchState("vowelsShifted",
        packsToAdd = listOf("vowelsShifted"),
        packsToOmit = listOf("vowels"),
    ),
    SwitchState("variantsOfCapitalA",
        packsToAdd = listOf("variantsOfCapitalA"),
        packsToOmit = listOf("vowelsShifted"),
    ),
    SwitchState("variantsOfCapitalE",
        packsToAdd = listOf("variantsOfCapitalE"),
        packsToOmit = listOf("vowelsShifted"),
    ),
    SwitchState("variantsOfCapitalI",
        packsToAdd = listOf("variantsOfCapitalI"),
        packsToOmit = listOf("vowelsShifted"),
    ),
    SwitchState("variantsOfCapitalO",
        packsToAdd = listOf("variantsOfCapitalO"),
        packsToOmit = listOf("vowelsShifted"),
    ),
    SwitchState("variantsOfCapitalU",
        packsToAdd = listOf("variantsOfCapitalU"),
        packsToOmit = listOf("vowelsShifted"),
    ),
    SwitchState("IPA",
        packsToAdd = listOf("IPA"),
    ),
)


class Keyboard (private val stack: Stack,
                private var emitInputCall: (input: KeyboardInput) -> Unit = {},
                private var playSoundCall: (soundResource: String) -> Unit = {},
                private var logger: Logger) {

    val corePacks = listOf<String>("main")
    var activeSwitchStates = mutableListOf<String>("latin")

    var inputState = InputState()

    var isBlockInput = false
    var gestureStartInputkey = Inputkey()
    var longPressStartInputkey = Inputkey()
    var currentGestureName = ""
    var currentGestureFirstPointerStartPosition = Pair(0F, 0F)
    var currentGestureSecondPointerStartPosition = Pair(0F, 0F)

    var isSoundEnabled = false

    var touchProcessor = TouchProcessor(logger)
    var graphics = Graphics()


    fun update(targetSurface: Surface2D, touches: List<Map<String, String>>): Surface2D {

        // Graphic essentials
        var surface = targetSurface
        val buttonSize = Pair(surface.width / 6F, surface.height / 4F)

        // Update input state
        inputState = touchProcessor.updateInputState(
            inputState, touches, buttonSize)

        // Reset check
        if (inputState.isResetTriggered) {
            // Reset input state
            inputState = InputState()
            // Reset variables
            isBlockInput = false
            currentGestureName = ""
            // Reset packs
            activeSwitchStates = mutableListOf<String>("latin")
        }

        // Calculate active packs
        val activePacks = corePacks.toMutableList()
        var packsToAdd = mutableListOf<String>()
        var packsToOmit = mutableListOf<String>()
        val switchStatesToProcess = activeSwitchStates + inputState.oneInputSwitches
        switchStatesToProcess.forEach { activeSwitchState ->
            val switchState = switchStates.find { it.name == activeSwitchState }
            if (switchState != null) {
                packsToAdd += switchState.packsToAdd
                packsToOmit += switchState.packsToOmit
            }
        }
        packsToAdd = packsToAdd.distinct().toMutableList()
        packsToOmit = packsToOmit.distinct().toMutableList()
        activePacks += packsToAdd
        activePacks.removeAll{ it in packsToOmit }

        // Get input keys
        val inputkeys = mutableListOf<Inputkey>()
        keyboardStack.getPacksByName(activePacks).forEach { pack ->
            pack.inputkeys.forEach { inputkey ->
                if (inputkey != null) {
                    inputkeys += inputkey
                }
            }
        }

        // Get inputkeys for render
        val inputkeysToRender = mutableListOf<Inputkey>()
        inputkeys.forEach { inputkey ->

            /*logger.log("AAAA", "INPUTKEY: ${inputkey.previewText} ${inputState.getPointerCount()} " +
                    "${inputState.firstPointer?.gridPosition} ${inputState.secondPointer?.gridPosition}")*/

            // 0 pointers
            if (inputState.getPointerCount() == 0 && (
                        inputkey.firstPosition == null || inputkey.secondPosition == null)) {
                inputkeysToRender += inputkey
            }

            // 1 pointer
            if (inputState.getPointerCount() == 1 && (
                        (inputState.firstPointer!!.gridPosition == inputkey.firstPosition && inputkey.secondPosition != null) ||
                        (inputState.firstPointer!!.gridPosition == inputkey.secondPosition && inputkey.firstPosition != null))) {
                inputkeysToRender += inputkey
            }
        }

        // Draw background
        surface.drawObject(Rectangle(0F, 0F,
            surface.width.toFloat(), surface.height.toFloat(), Color.BLACK))

        // Draw grid
        surface = graphics.drawGrid(surface, 0F, 0F, surface.width.toFloat(),
            surface.height.toFloat(), 4, 6, 4F)

        // Draw keys
        inputkeysToRender.forEach { inputkey ->
            if (inputkey.firstPosition != null) {
                surface = graphics.drawInputkey(
                    surface,
                    inputkey,
                    Pair(buttonSize.first * (inputkey.firstPosition.first - 1),
                        buttonSize.second * (inputkey.firstPosition.second)),
                    buttonSize
                )
            }
            if (inputkey.secondPosition != null) {
                surface = graphics.drawInputkey(
                    surface,
                    inputkey,
                    Pair(buttonSize.first * (inputkey.secondPosition.first - 1),
                        buttonSize.second * (inputkey.secondPosition.second)),
                    buttonSize
                )
            }
        }

        // Input
        if (inputState.isInputTriggered) {

            // Get inputkey
            val inputKey = getInputkey(inputkeys)
            if (inputKey != null && !isBlockInput) {

                logger.log("AAAA", "INPUT!: ${inputKey.previewText}, ${inputState.getPointerCount()}")


                // Initiate Input
                emitInputCall(
                    KeyboardInput(
                        inputText = inputKey.inputText,
                        actions = inputKey.actions,
                        amount = inputKey.amount
                    )
                )

                // Play sound
                if (inputKey.inputText != null && isSoundEnabled) {
                    playSoundCall("vine-boom.wav")
                }

                // One input switches
                inputState.oneInputSwitches = mutableListOf()
                if (inputKey.oneInputSwitches != null) {
                    inputState.oneInputSwitches += inputKey.oneInputSwitches.toMutableList()
                }

                // Switches
                if (inputKey.isRemoveAllSwitchesFirst == true) {
                    activeSwitchStates.clear()
                }
                if (inputKey.switchesToAdd != null) {
                    activeSwitchStates += inputKey.switchesToAdd
                }
                if (inputKey.switchesToRemove != null) {
                    activeSwitchStates.removeAll(inputKey.switchesToRemove)
                }

                // Actions
                inputKey.actions?.forEach { action ->
                    processAction(action)
                }
            }

            // Set input state
            inputState.firstPointer = null
            inputState.secondPointer = null
            inputState.isInputTriggered = false
        }

        // Gestures
        // Enter a gesture state
        if (inputState.isJustPressed) {
            val inputKey = getInputkey(inputkeys)
            if (inputKey != null) {
                if (inputKey.gesture != null) {
                    // Set state
                    inputState.isInGesture = true
                    currentGestureName = inputKey.gesture
                    currentGestureFirstPointerStartPosition = inputState.firstPointer?.position ?: Pair(0F, 0F)
                    gestureStartInputkey = inputKey
                }
            }
        }
        // Exit a gesture state
        if (inputState.getPointerCount() == 0) {
            inputState.isInGesture = false
        }
        // Log a gesture state
        //logger.log("AAAA", "GESTURE: ${inputState.isInGesture} ${inputState.getPointerCount()} $currentGestureName")

        // Draw pointers
        if (!inputState.isInGesture) {
            if (inputState.firstPointer != null) {
                graphics.drawTouch(
                    surface, 0F, 0F, surface.width.toFloat(),
                    surface.height.toFloat(),
                    inputState.firstPointer!!.gridPosition.first - 1,
                    inputState.firstPointer!!.gridPosition.second - 1,
                    4, 6
                )
            }
            if (inputState.secondPointer != null) {
                graphics.drawTouch(
                    surface, 0F, 0F, surface.width.toFloat(),
                    surface.height.toFloat(),
                    inputState.secondPointer!!.gridPosition.first - 1,
                    inputState.secondPointer!!.gridPosition.second - 1,
                    4, 6
                )
            }
        }

        // Draw gesture pointers
        if (inputState.isInGesture) {
            if (inputState.firstPointer != null) {
                surface.drawObject(
                    Circle(
                        inputState.firstPointer!!.position.first,
                        inputState.firstPointer!!.position.second,
                        surface.width / 18F, Color.YELLOW
                    )
                )
            }
            if (inputState.secondPointer != null) {
                surface.drawObject(
                    Circle(
                        inputState.secondPointer!!.position.first,
                        inputState.secondPointer!!.position.second,
                        surface.width / 18F, Color.YELLOW
                    )
                )
            }
        }

        // Process
        surface = updateGestures(surface)

        // Long press actions
        // Enter a long press state
        val inputKey = getInputkey(inputkeys)
        if (inputState.isJustPressed) {
            if (inputKey != null) {
                if (inputKey.actions?.isNotEmpty() ?: false) {
                    longPressStartInputkey = inputKey
                    if (!longPressAction.isActive) {
                        longPressAction = CoroutineScope(Dispatchers.Main)
                        longPressAction.launch {
                            delay(333.milliseconds)
                            while (isActive) {
                                processLongPressActions()
                                delay(50.milliseconds)
                            }
                        }
                    }
                }
            }
        }
        // Exit a long press state
        if ((inputState.getPointerCount() == 0) ||
                (inputKey != longPressStartInputkey)) {
            longPressAction.cancel()
        }

        // Return
        return surface
    }


    fun getInputkey(inputkeys: List<Inputkey>): Inputkey? {
        return inputkeys.find { inputkey ->

            // Input key click
            if (inputState.getPointerCount() == 1) {

                // 1 pointer
                (inputkey.firstPosition == inputState.firstPointer!!.gridPosition && inputkey.secondPosition == null) ||
                (inputkey.secondPosition == inputState.firstPointer!!.gridPosition && inputkey.firstPosition == null)

            } else if (inputState.getPointerCount() == 2) {

                // 2 pointers
                (inputkey.firstPosition == inputState.firstPointer!!.gridPosition &&
                inputkey.secondPosition == inputState.secondPointer!!.gridPosition) ||
                (inputkey.firstPosition == inputState.secondPointer!!.gridPosition &&
                inputkey.secondPosition == inputState.firstPointer!!.gridPosition)

            } else {
                false
            }
        }
    }


    fun processAction(action: String) {

        // Sound
        if (action == "switchTheSound") {
            isSoundEnabled = !isSoundEnabled
            logger.log("AAAA", "SOUND: $isSoundEnabled")
        }
    }

    var currentGestureStepsDone = 0

    fun gestureDistanceToSteps(surface: Surface2D, gestureDistance: Float, speed: Float, minDistance: Float): Int {
        val step = surface.width / speed
        var steps = round((gestureDistance - minDistance) / step).toInt()
        if (steps < 0) { steps = 0 }
        return steps
    }

    var lastGestureDistance = 0F
    var lastGesturePointerCount = 0
    var lastIsInGesture = false

    fun updateGestures(targetSurface: Surface2D): Surface2D {
        val surface = targetSurface

        // Calculate gesture distance
        var gestureDistance = 0F
        val minDistance = surface.width / 12F
        // 1 pointer
        if (inputState.getPointerCount() == 1 && lastGesturePointerCount == 1) {
            val offset = Pair(
                inputState.firstPointer!!.position.first - currentGestureFirstPointerStartPosition.first,
                inputState.firstPointer!!.position.second - currentGestureFirstPointerStartPosition.second
            )
            gestureDistance = sqrt(offset.first * offset.first + offset.second * offset.second)
        }
        // 2 pointers
        if (inputState.getPointerCount() == 2) {
            if (lastGesturePointerCount == 1) {
                currentGestureSecondPointerStartPosition = inputState.secondPointer!!.position
            }
            val offset = Pair(
                inputState.firstPointer!!.position.first - currentGestureFirstPointerStartPosition.first,
                inputState.firstPointer!!.position.second - currentGestureFirstPointerStartPosition.second
            )
            val offset2 = Pair(
                inputState.secondPointer!!.position.first - currentGestureSecondPointerStartPosition.first,
                inputState.secondPointer!!.position.second - currentGestureSecondPointerStartPosition.second
            )
            gestureDistance = sqrt(offset.first * offset.first + offset.second * offset.second) +
                    sqrt(offset2.first * offset2.first + offset2.second * offset2.second)
            // Check state
            if (lastGesturePointerCount == 1) {
                // Reset last gesture distance
                lastGestureDistance = gestureDistance
            }
        }
        // Reset state
        if (inputState.getPointerCount() == 0 || (inputState.getPointerCount() == 2 && inputState.touchCount != 2)) {
            currentGestureStepsDone = 0
            inputState.isInGesture = false
        }

        // Block input
        isBlockInput = inputState.isInGesture && gestureDistance > minDistance

        // Gestures
        /*logger.log("AAAA", "GESTURE: $isInGesture $gestureDistance " +
                "$currentGestureName ${gestureDistanceToSteps(surface, gestureDistance, 24F, minDistance * 2)}")*/

        // Delete
        if (inputState.isInGesture && currentGestureName == "deleteMultipleCharactersFromTheLeft") {
            val gestureSteps = gestureDistanceToSteps(surface, gestureDistance - minDistance, 128F, minDistance)
            val unprocessedSteps = gestureSteps - currentGestureStepsDone
            if (unprocessedSteps != 0) {
                emitInputCall(
                    KeyboardInput(
                        actions = listOf("moveSelectionLeft"),
                        amount = unprocessedSteps
                    )
                )
            }
            currentGestureStepsDone = gestureSteps
        }
        if ((!inputState.isInGesture && lastIsInGesture) && currentGestureName == "deleteMultipleCharactersFromTheLeft") {
            emitInputCall(KeyboardInput(actions = listOf("deleteSelection")))
        }

        // Move cursor horizontally
        if (inputState.isInGesture && currentGestureName == "moveCursorHorizontally") {
            val gestureSteps = gestureDistanceToSteps(surface, gestureDistance - minDistance, 24F, minDistance)
            val unprocessedSteps = gestureSteps - currentGestureStepsDone
            if (unprocessedSteps != 0) {

                emitInputCall(
                    KeyboardInput(
                        actions = listOf("moveCursorHorizontally"),
                        amount = gestureStartInputkey.amount?.times(unprocessedSteps) ?: 1
                    )
                )
            }
            currentGestureStepsDone = gestureSteps
        }

        // Move cursor vertically
        if (inputState.isInGesture && currentGestureName == "moveCursorVertically") {
            val gestureSteps = gestureDistanceToSteps(surface, gestureDistance - minDistance, 24F, minDistance)
            val unprocessedSteps = gestureSteps - currentGestureStepsDone
            if (unprocessedSteps != 0) {

                emitInputCall(
                    KeyboardInput(
                        actions = listOf("moveCursorVertically"),
                        amount = gestureStartInputkey.amount?.times(unprocessedSteps) ?: 1
                    )
                )
            }
            currentGestureStepsDone = gestureSteps
        }


        // Set state
        lastIsInGesture = inputState.isInGesture
        lastGestureDistance = gestureDistance
        lastGesturePointerCount = inputState.getPointerCount()

        return surface
    }


    var longPressAction = CoroutineScope(Dispatchers.Main)
    fun processLongPressActions() {
        if (longPressStartInputkey.actions?.contains("deleteMultipleCharactersFromTheLeft") ?: false) {
            emitInputCall(KeyboardInput(actions = listOf("deleteCharacterFromTheLeft")))
        }
    }
}
