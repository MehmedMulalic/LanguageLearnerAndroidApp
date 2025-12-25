package com.example.myapplication.ui.auth.signup

sealed class SignupState {
    object Authenticated: SignupState()
    object Unauthenticated: SignupState()
    data class Error(val message: String) : SignupState()
}