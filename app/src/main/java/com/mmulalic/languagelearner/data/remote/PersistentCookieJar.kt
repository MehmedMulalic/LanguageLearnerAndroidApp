package com.mmulalic.languagelearner.data.remote

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import javax.inject.Inject

class PersistentCookieJar @Inject constructor(
    private val storage: CookieStorage,
    @param:ApplicationScope private val scope: CoroutineScope
): CookieJar {
    @Volatile
    private var cache: List<Cookie> = emptyList()

    override fun saveFromResponse(
        url: HttpUrl,
        cookies: List<Cookie>
    ) {
        val merged = cache.toMutableList()
        for (newCookie in cookies) {
            merged.removeAll { it.name == newCookie.name && it.domain == newCookie.domain && it.path == newCookie.path }
            merged.add(newCookie)
        }
        cache = merged.filter { !it.hasExpired() }

        scope.launch { storage.saveCookies(cookies) }
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return cache
    }

    private fun Cookie.hasExpired() = expiresAt < System.currentTimeMillis()

    suspend fun initialize() {
        cache = storage.loadCookies()
    }
}