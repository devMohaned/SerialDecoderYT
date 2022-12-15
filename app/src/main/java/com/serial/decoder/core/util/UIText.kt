package com.serial.decoder.core.util

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

/**
 *
 *
 * Source: PhilippLackner's Youtube Channel https://www.youtube.com/watch?v=mB1Lej0aDus
 *
 *
 */
sealed class UIText {
    data class DynamicString(val value: String) : UIText()
    class StringResource(@StringRes val resId: Int, vararg val args: Any) : UIText()

    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> stringResource(id = resId, *args)
        }
    }

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(resId, *args)
        }
    }
}