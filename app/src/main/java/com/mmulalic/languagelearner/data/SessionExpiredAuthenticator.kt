package com.mmulalic.languagelearner.data

import com.mmulalic.languagelearner.data.remote.CookieStorage
import com.mmulalic.languagelearner.data.repository.AuthRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class SessionExpiredAuthenticator @Inject constructor(
    private val authRepository: AuthRepository,
    private val cookieStorage: CookieStorage
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code == 401) {
            runBlocking { cookieStorage.clearCookies() }
            authRepository.setUnauthenticated()
        }
        return null
    }
}