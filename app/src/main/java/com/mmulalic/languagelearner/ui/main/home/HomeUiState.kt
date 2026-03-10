package com.mmulalic.languagelearner.ui.main.home

import com.mmulalic.languagelearner.data.model.UserData

sealed interface HomeUiState {
    object Loading : HomeUiState
    data class Success(val userData: UserData) : HomeUiState
    data class Error(val message: String) : HomeUiState
}