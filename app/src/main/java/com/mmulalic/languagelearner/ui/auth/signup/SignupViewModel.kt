package com.mmulalic.languagelearner.ui.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmulalic.languagelearner.data.model.exceptions.UsernameTakenException
import com.mmulalic.languagelearner.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState: StateFlow<SignupUiState> = _uiState

    fun onUsernameChange(value: String) {
        if (value.length > 64) { return }
        _uiState.value = _uiState.value.copy(username = value)
    }

    fun onPasswordChange(value: String) {
        if (value.length > 64) { return }
        _uiState.value = _uiState.value.copy(password = value)
    }
    fun onConfirmPasswordChange(value: String) {
        if (value.length > 64) { return }
        _uiState.value = _uiState.value.copy(confirmPassword = value)
    }

    fun signup() {
        val current = _uiState.value

        SignupValidator.validateUsername(current.username)?.let {
            _uiState.value = current.copy(errorMessage = it)
            return
        }
        SignupValidator.validatePassword(current.password, current.confirmPassword)?.let {
            _uiState.value = current.copy(errorMessage = it)
            return
        }

        viewModelScope.launch {
            _uiState.value = current.copy(isLoading = true, errorMessage = null)

            try {
                repository.signup(current.username, current.password)

                _uiState.value = SignupUiState(isAuthenticated = true)
            } catch (e: UsernameTakenException) {
                _uiState.value = current.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
            catch (e: Exception) {
                _uiState.value = current.copy(
                    isLoading = false,
                    errorMessage = "Signup failed. Error message: ${e.message}"
                )
            }
        }
    }
}