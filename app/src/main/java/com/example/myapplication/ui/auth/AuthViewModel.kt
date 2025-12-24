package com.example.myapplication.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow<AuthState>(AuthState.Loading)
    val state: StateFlow<AuthState> = _state

    init {
        viewModelScope.launch {
            repository.token.collect { token ->
                _state.value =
                    when (token) {
                        null -> AuthState.Unauthenticated
                        "errorCredentials" -> AuthState.Error("Bad credentials")
                        else -> AuthState.Authenticated
                    }
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                repository.login(email, password)
            } catch (e: Exception) {
                _state.value = AuthState.Error("Login failed: ${e.message}")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}