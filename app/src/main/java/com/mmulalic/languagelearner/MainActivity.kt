package com.mmulalic.languagelearner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.mmulalic.languagelearner.ui.auth.AuthenticationScreen
import com.mmulalic.languagelearner.ui.main.profile.personalization.ThemeViewModel
import com.mmulalic.languagelearner.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val themeViewModel: ThemeViewModel = hiltViewModel()
            val themeOption by themeViewModel.themeOption.collectAsState()

            AppTheme(themeOption = themeOption) {
                AuthenticationScreen()
            }
        }
    }
}