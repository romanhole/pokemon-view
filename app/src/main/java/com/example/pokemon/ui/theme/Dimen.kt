package com.example.pokemon.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Dimen(
    val spaceXXSmall: Dp = 2.dp,
    val spaceExtraSmall: Dp = 4.dp,
    val spaceSmall: Dp = 8.dp,
    val spaceMedium: Dp = 10.dp,
    val default: Dp = 16.dp,
    val spaceExtraMedium: Dp = 20.dp,
    val spaceXXMedium: Dp = 24.dp,
    val spaceLarge: Dp = 32.dp,
    val spaceExtraLarge: Dp = 40.dp,
    val spaceXXLarge: Dp = 56.dp,
    val spaceXXXLarge: Dp = 72.dp,

    val fontXXSmall: TextUnit = 10.sp,
    val fontSmall: TextUnit = 12.sp,
    val fontDefault: TextUnit = 14.sp,
    val fontMedium: TextUnit = 16.sp,
    val fontLarge: TextUnit = 18.sp,
    val fontExtraLarge: TextUnit = 24.sp,
)
