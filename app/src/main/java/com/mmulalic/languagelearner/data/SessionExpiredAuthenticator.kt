package com.mmulalic.languagelearner.data

import com.mmulalic.languagelearner.data.remote.ApplicationScope
import com.mmulalic.languagelearner.data.remote.CookieStorage
import com.mmulalic.languagelearner.data.repository.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class SessionExpiredAuthenticator @Inject constructor(
    private val sessionManager: SessionManager,
    private val cookieStorage: CookieStorage,
    @ApplicationScope private val scope: CoroutineScope
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code == 401) {
            runBlocking { cookieStorage.clearCookies() }
            sessionManager.setUnauthenticated()
            scope.launch { sessionManager.notifySessionExpired() }
        }
        return null
    }
}