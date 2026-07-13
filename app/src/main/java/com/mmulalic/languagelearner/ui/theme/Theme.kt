package com.mmulalic.languagelearner.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

private val pearlAqua = Color(0xFF99E1D9)
private val tropicalTeal = Color(0xFF70ABAF)
private val lavenderBlush = Color(0xFFFFEAEC)
private val cottonCandy = Color(0xFFF39A9D)
private val deepWine = Color(0xFF773344)

val LightColorScheme = lightColorScheme(
    primary = deepWine,
    secondary = tropicalTeal,
    tertiary = cottonCandy
)

val DarkColorScheme = darkColorScheme(
    primary = pearlAqua,
    secondary = tropicalTeal,
    tertiary = cottonCandy
)
