package com.mmulalic.languagelearner.ui.main.profile.personalization

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ThemePreference @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val THEME_MODE_KEY = stringPreferencesKey("theme_mode")

    val themeOption: Flow<ThemeOption> = dataStore.data.map { prefs ->
        ThemeOption.valueOf(prefs[THEME_MODE_KEY] ?: ThemeOption.SYSTEM.name)
    }

    suspend fun setThemeOption(option: ThemeOption) {
        dataStore.edit { prefs ->
            prefs[THEME_MODE_KEY] = option.name
        }
    }
}