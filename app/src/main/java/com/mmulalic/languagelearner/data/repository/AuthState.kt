package com.mmulalic.languagelearner.data.repository

sealed interface AuthState {
    object Unknown : AuthState
    object Authenticated : AuthState
    object Unauthenticated : AuthState
}