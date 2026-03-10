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
        val u = current.username
        val p = current.password

        var error = when {
            u.length < 3 -> "Username is too short."
            u.length > 64 -> "Username is too long."
            u.any { it.isWhitespace() } -> "Username may not include whitespace."
            else -> null
        }
        if (error != null) {
            _uiState.value = current.copy(errorMessage = error)
            return
        }

        error = when {
            p != current.confirmPassword -> "Passwords do not match."
            p.length < 3 -> "Password is too short."
            p.length > 72 -> "Password is too long."
            p.none { it.isLowerCase() } -> "Password requires a lower letter."
            p.none { it.isUpperCase() } -> "Password requires a capital letter."
            p.none { it.isDigit() } -> "Password requires a digit."
            else -> null
        }
        if (error != null) {
            _uiState.value = current.copy(errorMessage = error)
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