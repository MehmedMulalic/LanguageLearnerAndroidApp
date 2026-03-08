package com.mmulalic.languagelearner.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmulalic.languagelearner.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow<LoginState>(LoginState.Loading)
    val state: StateFlow<LoginState> = _state

    init {
        viewModelScope.launch {
            repository.cookies.collect { cookieValue ->
                _state.value =
                    when {
                        cookieValue.isEmpty() -> LoginState.Unauthenticated
//                        "errorCredentials" -> LoginState.Error("Bad credentials")
                        else -> LoginState.Authenticated
                    }
            }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                repository.login(username, password)
            } catch (e: Exception) {
                _state.value = LoginState.Error("Login failed: ${e.message}")
            }
        }
    }
}