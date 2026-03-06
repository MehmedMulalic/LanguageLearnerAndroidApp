package com.mmulalic.languagelearner.ui.home

sealed interface HomeUiState {
    object Loading : HomeUiState
    data class Success(val username: String) : HomeUiState
    data class Error(val message: String) : HomeUiState
}