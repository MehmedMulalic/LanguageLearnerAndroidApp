package com.mmulalic.languagelearner.ui.profile

sealed class ProfileState {
    object Loading: ProfileState()
    object LoggedIn: ProfileState()
    data class Error(val message: String): ProfileState()
}