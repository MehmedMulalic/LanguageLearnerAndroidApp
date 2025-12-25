package com.example.myapplication.ui.auth.login

sealed class LoginState {
    object Loading : LoginState()
    object Authenticated : LoginState()
    object Unauthenticated : LoginState()
    data class Error(val message: String) : LoginState()
}