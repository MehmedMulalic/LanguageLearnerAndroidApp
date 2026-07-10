package com.mmulalic.languagelearner.data

import com.mmulalic.languagelearner.data.model.SerializableCookie
import com.mmulalic.languagelearner.data.remote.CookieStorage
import com.mmulalic.languagelearner.data.remote.DataStoreCookieStorage
import com.mmulalic.languagelearner.data.repository.AuthState
import com.mmulalic.languagelearner.data.repository.SessionManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okhttp3.Cookie
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import org.junit.Assert.*
import org.junit.Test

class SessionExpiredAuthenticatorTest {
    @Test
    fun `401 response clears cookies and emits session expired`() = runTest {
        val fakeSessionManager = SessionManager()
        val fakeCookieStorage = FakeCookieStorage()
        val authenticator = SessionExpiredAuthenticator(fakeSessionManager, fakeCookieStorage, this)

        val response = mockResponseWithCode(401)
        authenticator.authenticate(null, response)
        advanceUntilIdle()

        assertEquals(AuthState.Unauthenticated, fakeSessionManager.authState.value)
        assertTrue(fakeCookieStorage.wasCleared)
    }

    @Test
    fun `non-401 response does nothing`() = runTest {
        val fakeSessionManager = SessionManager()
        val fakeCookieStorage = FakeCookieStorage()
        val authenticator = SessionExpiredAuthenticator(fakeSessionManager, fakeCookieStorage, this)

        authenticator.authenticate(null, mockResponseWithCode(500))
        advanceUntilIdle()

        assertEquals(AuthState.Unknown, fakeSessionManager.authState.value)
        assertFalse(fakeCookieStorage.wasCleared)
    }

    fun mockResponseWithCode(code: Int): Response {
        val request = Request.Builder()
            .url("https://example.com/")
            .build()

        return Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(code)
            .message("Unauthorized")
            .build()
    }
}

class FakeCookieStorage : CookieStorage {
    private val _cookies = MutableStateFlow<List<Cookie>>(emptyList())
    override val cookies: StateFlow<List<Cookie>> = _cookies.asStateFlow()
    var wasCleared = false
        private set

    override suspend fun saveCookies(cookies: List<Cookie>) {
        _cookies.value = cookies
    }
    override suspend fun loadCookies(): List<Cookie> = _cookies.value
    override suspend fun clearCookies() {
        wasCleared = true
        _cookies.value = emptyList()
    }

}