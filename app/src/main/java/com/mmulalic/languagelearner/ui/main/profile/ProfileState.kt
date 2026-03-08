package com.mmulalic.languagelearner.ui.main.profile

sealed class ProfileState {
    object Loading: ProfileState()
    object LoggedIn: ProfileState()
    object LoggedOut: ProfileState()
    data class Error(val message: String): ProfileState()
}