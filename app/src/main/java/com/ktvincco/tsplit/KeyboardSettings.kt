package com.ktvincco.tsplit

import android.content.Context
import android.content.res.Configuration
import android.view.View

enum class Setting(
    val key: String,
    val defaultDp: Int,
    val minDp: Int,
    val maxDp: Int
) {
    PORTRAIT_HEIGHT("keyboard_height_dp", 300, 5, 1200),
    LANDSCAPE_HEIGHT("keyboard_height_landscape_dp", 200, 5, 1200),
    BOTTOM_LINE_HEIGHT("bottom_line_height_dp", 50, 5, 1200),
    BOTTOM_LINE_LANDSCAPE_HEIGHT("bottom_line_height_landscape_dp", 30, 5, 1200)
}

object KeyboardSettings {
    const val PREFS_NAME = "keyboard_prefs"
    const val KEY_BOTTOM_LINE_ENABLED = "bottom_line_enabled"
    const val KEY_BOTTOM_LINE_LANDSCAPE_ENABLED = "bottom_line_landscape_enabled"

    private fun prefs(context: Context) =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun get(context: Context, setting: Setting): Int {
        return prefs(context).getInt(setting.key, setting.defaultDp)
            .coerceIn(setting.minDp, setting.maxDp)
    }

    /** Clamps, saves and returns the value that was actually stored. */
    fun set(context: Context, setting: Setting, dp: Int): Int {
        val clamped = dp.coerceIn(setting.minDp, setting.maxDp)
        prefs(context).edit().putInt(setting.key, clamped).apply()
        return clamped
    }

    /** Valid number within range -> that number, anything else -> the default. */
    fun parse(setting: Setting, text: String?): Int {
        val value = text?.trim()?.toIntOrNull()
        return if (value != null && value in setting.minDp..setting.maxDp) value
        else setting.defaultDp
    }

    fun isBottomLineEnabled(context: Context, landscape: Boolean): Boolean {
        val key = if (landscape) KEY_BOTTOM_LINE_LANDSCAPE_ENABLED else KEY_BOTTOM_LINE_ENABLED
        return prefs(context).getBoolean(key, true)
    }

    fun setBottomLineEnabled(context: Context, landscape: Boolean, enabled: Boolean) {
        val key = if (landscape) KEY_BOTTOM_LINE_LANDSCAPE_ENABLED else KEY_BOTTOM_LINE_ENABLED
        prefs(context).edit().putBoolean(key, enabled).apply()
    }

    fun isLandscape(context: Context): Boolean =
        context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    fun dpToPx(context: Context, dp: Int): Int =
        (dp * context.resources.displayMetrics.density).toInt()

    /** Applies saved heights to a keyboard layout (used by both the app and the IME). */
    fun applyToKeyboardView(context: Context, root: View?) {
        root ?: return

        val landscape = isLandscape(context)

        // Touch surface
        val heightSetting = if (landscape) Setting.LANDSCAPE_HEIGHT else Setting.PORTRAIT_HEIGHT
        val touchSurface = root.findViewById<View>(R.id.imageView1)
        touchSurface?.layoutParams = touchSurface?.layoutParams?.apply {
            height = dpToPx(context, get(context, heightSetting))
        }

        // Bottom line
        val bottomLineSetting =
            if (landscape) Setting.BOTTOM_LINE_LANDSCAPE_HEIGHT else Setting.BOTTOM_LINE_HEIGHT
        val bottomLine = root.findViewById<View>(R.id.bottomLine)
        bottomLine?.layoutParams = bottomLine?.layoutParams?.apply {
            height = if (isBottomLineEnabled(context, landscape))
                dpToPx(context, get(context, bottomLineSetting)) else 0
        }
    }
}