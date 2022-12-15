package com.serial.decoder.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.serial.decoder.R


val Oswald = FontFamily(
    Font(R.font.oswald_regular, FontWeight.Normal),
    Font(R.font.oswald_bold, FontWeight.Bold)

)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = Oswald,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h1 = TextStyle(
        fontFamily = Oswald, fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),
    h2 = TextStyle(
        fontFamily = Oswald, fontWeight = FontWeight.Normal,
        fontSize = 32.sp
    ),
    h3 = TextStyle(
        fontFamily = Oswald, fontWeight = FontWeight.Normal,
        color = Color.Black,
        fontSize = 20.sp
    ),
    subtitle1 = TextStyle(fontFamily = Oswald, fontWeight = FontWeight.Normal, fontSize = 14.sp),
    button = TextStyle(fontFamily = Oswald, fontWeight = FontWeight.Normal, fontSize = 16.sp)

)