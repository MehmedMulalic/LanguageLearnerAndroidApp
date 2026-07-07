package com.mmulalic.languagelearner.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmulalic.languagelearner.data.model.LoginResult
import com.mmulalic.languagelearner.data.repository.AuthRepository
import com.mmulalic.languagelearner.data.repository.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow<LoginState>(LoginState.Loading)
    val state: StateFlow<LoginState> = _state

    init {
        viewModelScope.launch {
            authRepository.authState.collect { auth ->
                _state.value = when (auth) {
                    AuthState.Authenticated -> LoginState.Authenticated
                    AuthState.Unauthenticated -> LoginState.Unauthenticated
                    AuthState.Unknown -> LoginState.Loading
                }
            }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            when (val result = authRepository.login(username, password)) {
                LoginResult.Success -> {}

                LoginResult.Error.NoInternet -> {
                    _state.value = LoginState.Error("Check your internet connection.")
                }

                LoginResult.Error.ServerError -> {
                    _state.value = LoginState.Error("Server is currently unavailable.")
                }

                LoginResult.Error.InvalidCredentials -> {
                    _state.value = LoginState.Error("Incorrect username or password.")
                }

                LoginResult.Error.Unknown -> {
                    _state.value = LoginState.Error("Something went wrong.")
                }
            }
        }
    }
}