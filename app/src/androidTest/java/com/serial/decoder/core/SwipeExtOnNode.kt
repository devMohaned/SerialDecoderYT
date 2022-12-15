package com.serial.decoder.core

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipe


fun SemanticsNodeInteraction.swipeHorizontalToTheEnd(
    isLTR: Boolean = true
) {
    performTouchInput {
        val startingX = if (isLTR) 600F else 0F
        val endingX = if (isLTR) 0F else 600F
        val startingY = 0F
        val endingY = 0F
        swipe(Offset(startingX, startingY), Offset(endingX, endingY))
    }
}