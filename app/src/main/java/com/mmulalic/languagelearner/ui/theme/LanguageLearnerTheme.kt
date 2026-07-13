package com.mmulalic.languagelearner.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import com.mmulalic.languagelearner.ui.main.profile.personalization.ThemeOption

@Composable
fun LanguageLearnerTheme(
    themeOption: ThemeOption = ThemeOption.SYSTEM,
    content: @Composable () -> Unit
) {
    val darkTheme = when (themeOption) {
        ThemeOption.SYSTEM -> isSystemInDarkTheme()
        ThemeOption.LIGHT -> false
        ThemeOption.DARK -> true
    }
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}