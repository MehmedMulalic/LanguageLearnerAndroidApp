package com.example.myapplication.ui.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow<SignupState>(SignupState.Unauthenticated)
    val state: StateFlow<SignupState> = _state

    fun signup(username: String, password: String) {
        viewModelScope.launch {
            try {
                repository.signup(username, password)
            } catch (e: Exception) {
                _state.value = SignupState.Error("Signup failed: ${e.message}")
            }
        }
    }
}