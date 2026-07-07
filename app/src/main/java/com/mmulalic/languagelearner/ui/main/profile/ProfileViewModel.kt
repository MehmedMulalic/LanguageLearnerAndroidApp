package com.mmulalic.languagelearner.ui.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmulalic.languagelearner.data.repository.AuthRepository
import com.mmulalic.languagelearner.data.repository.AuthState
import com.mmulalic.languagelearner.data.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val state: StateFlow<ProfileState> = _state

    init {
        viewModelScope.launch {
            authRepository.authState.collect { auth ->
                _state.value = when (auth) {
                    AuthState.Authenticated -> ProfileState.LoggedIn
                    AuthState.Unauthenticated -> ProfileState.LoggedOut
                    AuthState.Unknown -> ProfileState.Loading
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                profileRepository.logout()
                authRepository.logout()
            } catch (e: Exception) {
                _state.value = ProfileState.Error("Logout failed: ${e.message}")
            }
        }
    }
}