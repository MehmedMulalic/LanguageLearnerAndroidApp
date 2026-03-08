package com.mmulalic.languagelearner.data

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.cookieDataStore by preferencesDataStore("cookies")