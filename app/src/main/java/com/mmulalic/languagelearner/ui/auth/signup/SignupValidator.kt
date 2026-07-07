package com.mmulalic.languagelearner.ui.auth.signup

object SignupValidator {
    fun validateUsername(username: String): String? = when {
        username.length < 3 -> "Username is too short."
        username.length > 64 -> "Username is too long."
        username.any { it.isWhitespace() } -> "Username may not include whitespace."
        else -> null
    }
    fun validatePassword(password: String, confirmPassword: String): String? = when {
        password != confirmPassword -> "Passwords do not match."
        password.length < 3 -> "Password is too short."
        password.length > 72 -> "Password is too long."
        password.none { it.isLowerCase() } -> "Password requires a lower letter."
        password.none { it.isUpperCase() } -> "Password requires a capital letter."
        password.none { it.isDigit() } -> "Password requires a digit."
        else -> null
    }
}