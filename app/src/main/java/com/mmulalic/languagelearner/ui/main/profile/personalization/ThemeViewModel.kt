package com.mmulalic.languagelearner.ui.main.profile.personalization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val themePreference: ThemePreference
) : ViewModel() {
    val themeOption: StateFlow<ThemeOption> = themePreference.themeOption
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ThemeOption.SYSTEM)

    fun setThemeOption(option: ThemeOption) {
        viewModelScope.launch {
            themePreference.setThemeOption(option)
        }
    }
}