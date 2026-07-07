package com.mmulalic.languagelearner

import android.app.Application
import com.mmulalic.languagelearner.data.remote.ApplicationScope
import com.mmulalic.languagelearner.data.remote.PersistentCookieJar
import com.mmulalic.languagelearner.data.repository.SessionManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class LanguageLearner: Application() {
    @Inject lateinit var sessionManager: SessionManager
    @Inject lateinit var cookieJar: PersistentCookieJar
    @Inject @ApplicationScope lateinit var scope: CoroutineScope

    override fun onCreate() {
        super.onCreate()
        scope.launch {
            cookieJar.initialize()
            resolveInitialAuthState()
        }
    }

    private fun resolveInitialAuthState() {
        if (cookieJar.hasValidSession()) {
            sessionManager.setAuthenticated()
        } else {
            sessionManager.setUnauthenticated()
        }
    }
}