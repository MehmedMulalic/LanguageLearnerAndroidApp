package com.mmulalic.languagelearner.data.repository

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

sealed interface SessionEvent {
    object Expired: SessionEvent
}

@Singleton
class SessionManager @Inject constructor() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Unknown)
    private val _sessionEvents = MutableSharedFlow<SessionEvent>()
    val authState: StateFlow<AuthState> = _authState
    val sessionEvents: SharedFlow<SessionEvent> = _sessionEvents

    fun setAuthenticated() { _authState.value = AuthState.Authenticated }
    fun setUnauthenticated() { _authState.value = AuthState.Unauthenticated }

    suspend fun notifySessionExpired() {
        _sessionEvents.emit(SessionEvent.Expired)
    }
}