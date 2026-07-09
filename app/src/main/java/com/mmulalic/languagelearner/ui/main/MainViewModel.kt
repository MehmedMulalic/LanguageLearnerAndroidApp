package com.mmulalic.languagelearner.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmulalic.languagelearner.data.repository.AuthRepository
import com.mmulalic.languagelearner.data.repository.AuthState
import com.mmulalic.languagelearner.data.repository.SessionEvent
import com.mmulalic.languagelearner.data.repository.UserRepository
import com.mmulalic.languagelearner.ui.main.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState
    val authState: StateFlow<AuthState> = authRepository.authState
    val sessionEvents: SharedFlow<SessionEvent> = authRepository.sessionEvents

    init {
        loadUser()
        viewModelScope.launch {
            authState.collect { auth ->
                if (auth == AuthState.Unauthenticated) {
                    _uiState.value = HomeUiState.Loading
                }
            }
        }
    }

    private fun loadUser() {
        viewModelScope.launch {
            try {
                val userData = userRepository.getUserData()
                _uiState.value = HomeUiState.Success(userData)
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error("Failed to load user. Error: ${e.message}")
            }
        }
    }
}