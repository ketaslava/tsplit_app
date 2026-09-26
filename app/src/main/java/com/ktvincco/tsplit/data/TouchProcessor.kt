package com.ktvincco.tsplit.data

import android.util.Log
import kotlin.Float
import kotlin.String
import kotlin.collections.Map
import kotlin.collections.mutableListOf
import kotlin.math.round
import kotlin.text.toFloat
import kotlin.text.toInt


class Pointer (val position: Pair<Float, Float>, val gridPosition: Pair<Int, Int>)


class InputState (
    var firstPointer: Pointer? = null,
    var secondPointer: Pointer? = null,
    var isInputTriggered: Boolean = false,
    var isResetTriggered: Boolean = false,
    var oneInputSwitches: MutableList<String> = mutableListOf(),
    var lastPointerCount: Int = 0,
    var isJustPressed: Boolean = false,
    var isInGesture: Boolean = false,
    var touchCount: Int = 0
) {
    fun getPointerCount(): Int {
        var count = 0
        if (firstPointer != null) count++
        if (secondPointer != null) count++
        return count
    }
}


class TouchProcessor (val logger: Logger) {

    fun updateInputState(inputState: InputState, touches: List<Map<String, String>>,
                      inputkeySize: Pair<Float, Float>): InputState {

        // Convert touches to pointers
        if (touches.isNotEmpty()) {

            val firstTouch = touches.find { (it["id"] ?: "") == "0" }
            if (firstTouch != null) {
                inputState.firstPointer = touchToPointer(firstTouch, inputkeySize)
            }

            val secondTouch = touches.find { (it["id"] ?: "") == "1" }
            if (firstTouch != null && secondTouch != null) {
                inputState.secondPointer = touchToPointer(secondTouch, inputkeySize)
            }
        }

        // Input trigger
        if (touches.isEmpty() && (inputState.firstPointer != null ||
            inputState.secondPointer != null)) {
            inputState.isInputTriggered = true
        }

        // Just pressed
        if (inputState.isJustPressed) {
            inputState.isJustPressed = false
        }
        if (inputState.getPointerCount() > inputState.lastPointerCount) {
            inputState.isJustPressed = true
        }

        // Reset trigger
        if (touches.size >= 3) {
            inputState.isResetTriggered = true
        }

        // Update state
        inputState.touchCount = touches.size

        // Update last pointer count
        inputState.lastPointerCount = inputState.getPointerCount()

        return inputState
    }


    fun touchToPointer(touch: Map<String, String>, inputkeySize: Pair<Float, Float>): Pointer {
        return Pointer (
            Pair(
                touch["x"]?.toFloat()?: 0F,
                touch["y"]?.toFloat()?: 0F),
            Pair(
                touch["x"]?.toFloat()?.div(inputkeySize.first)?.toInt()?.plus(1) ?: 0,
                touch["y"]?.toFloat()?.div(inputkeySize.second)?.toInt()?.plus(1) ?: 0)
        )
    }
}