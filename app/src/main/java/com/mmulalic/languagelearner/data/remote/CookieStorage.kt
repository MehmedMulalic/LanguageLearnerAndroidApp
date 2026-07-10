package com.mmulalic.languagelearner.data.remote

import kotlinx.coroutines.flow.StateFlow
import okhttp3.Cookie

interface CookieStorage {
    val cookies: StateFlow<List<Cookie>>
    suspend fun saveCookies(cookies: List<Cookie>)
    suspend fun loadCookies(): List<Cookie>
    suspend fun clearCookies()
}