package com.mmulalic.languagelearner.ui.main.profile

data class ProfileActions(
    val onProfileClick: () -> Unit = {},
    val onEmailClick: () -> Unit = {},
    val onChangePasswordClick: () -> Unit = {},
    val onNotificationsClick: (Boolean) -> Unit = {},
    val onDarkModeClick: () -> Unit = {},
    val onLogoutClick: () -> Unit = {}
)
