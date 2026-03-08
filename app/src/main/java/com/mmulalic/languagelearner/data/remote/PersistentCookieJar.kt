package com.mmulalic.languagelearner.data.remote

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class PersistentCookieJar(
    private val storage: CookieStorage
): CookieJar {
    private var cache: List<Cookie> = emptyList()

    override fun saveFromResponse(
        url: HttpUrl,
        cookies: List<Cookie>
    ) {
        cache = cookies

        CoroutineScope(Dispatchers.IO).launch {
            storage.saveCookies(cookies)
        }
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return cache
    }

    suspend fun initialize() {
        cache = storage.loadCookies()
    }
}