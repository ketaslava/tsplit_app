package com.ktvincco.tsplit

import android.content.Context
import android.view.View

object KeyboardSettings {
    const val PREFS_NAME = "keyboard_prefs"
    const val KEY_KEYBOARD_HEIGHT_DP = "keyboard_height_dp"

    const val DEFAULT_HEIGHT_DP = 300
    const val MIN_HEIGHT_DP = 100
    const val MAX_HEIGHT_DP = 600

    fun getHeightDp(context: Context): Int {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getInt(KEY_KEYBOARD_HEIGHT_DP, DEFAULT_HEIGHT_DP)
            .coerceIn(MIN_HEIGHT_DP, MAX_HEIGHT_DP)
    }

    /** Saves the value (clamped) and returns the value actually stored. */
    fun setHeightDp(context: Context, dp: Int): Int {
        val clamped = dp.coerceIn(MIN_HEIGHT_DP, MAX_HEIGHT_DP)
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putInt(KEY_KEYBOARD_HEIGHT_DP, clamped)
            .apply()
        return clamped
    }

    /** Applies the saved height to the touch surface (imageView1). */
    fun applyHeight(context: Context, imageView: View?) {
        imageView ?: return
        val px = (getHeightDp(context) * context.resources.displayMetrics.density).toInt()
        imageView.layoutParams = imageView.layoutParams?.apply { height = px }
        imageView.requestLayout()
    }
}