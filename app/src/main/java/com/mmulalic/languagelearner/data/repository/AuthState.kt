package com.mmulalic.languagelearner.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

sealed interface AuthState {
    object Unknown : AuthState
    object Authenticated : AuthState
    object Unauthenticated : AuthState
}

@Singleton
class SessionManager @Inject constructor() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Unknown)
    val authState: StateFlow<AuthState> = _authState

    fun setAuthenticated() { _authState.value = AuthState.Authenticated }
    fun setUnauthenticated() { _authState.value = AuthState.Unauthenticated }
}