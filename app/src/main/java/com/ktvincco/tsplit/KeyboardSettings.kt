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
    PORTRAIT_HEIGHT("keyboard_height_dp", 300, 50, 1200),
    LANDSCAPE_HEIGHT("keyboard_height_landscape_dp", 200, 50, 1200),
    BOTTOM_LINE_HEIGHT("bottom_line_height_dp", 50, 5, 1200)
}

object KeyboardSettings {
    const val PREFS_NAME = "keyboard_prefs"
    const val KEY_BOTTOM_LINE_ENABLED = "bottom_line_enabled"

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

    /** Valid number within range -> that number, anything else -> the default (300 for heights). */
    fun parse(setting: Setting, text: String?): Int {
        val value = text?.trim()?.toIntOrNull()
        return if (value != null && value in setting.minDp..setting.maxDp) value
        else setting.defaultDp
    }

    fun isBottomLineEnabled(context: Context): Boolean =
        prefs(context).getBoolean(KEY_BOTTOM_LINE_ENABLED, true)

    fun setBottomLineEnabled(context: Context, enabled: Boolean) {
        prefs(context).edit().putBoolean(KEY_BOTTOM_LINE_ENABLED, enabled).apply()
    }

    fun isLandscape(context: Context): Boolean =
        context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    fun dpToPx(context: Context, dp: Int): Int =
        (dp * context.resources.displayMetrics.density).toInt()

    /** Applies saved heights to a keyboard layout (used by both the app and the IME). */
    fun applyToKeyboardView(context: Context, root: View?) {
        root ?: return

        val heightSetting =
            if (isLandscape(context)) Setting.LANDSCAPE_HEIGHT else Setting.PORTRAIT_HEIGHT
        val touchSurface = root.findViewById<View>(R.id.imageView1)
        touchSurface?.layoutParams = touchSurface?.layoutParams?.apply {
            height = dpToPx(context, get(context, heightSetting))
        }

        val bottomLine = root.findViewById<View>(R.id.bottomLine)
        bottomLine?.layoutParams = bottomLine?.layoutParams?.apply {
            height = if (isBottomLineEnabled(context))
                dpToPx(context, get(context, Setting.BOTTOM_LINE_HEIGHT)) else 0
        }
    }
}