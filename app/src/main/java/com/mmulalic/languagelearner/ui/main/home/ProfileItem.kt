package com.mmulalic.languagelearner.ui.main.home

import androidx.compose.ui.graphics.vector.ImageVector

data class ProfileItem(
    val label: String = "",
    val onClick: () -> Unit,
    val icon: ImageVector? = null
)