package com.mmulalic.languagelearner.data.remote

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mmulalic.languagelearner.data.cookieDataStore
import com.mmulalic.languagelearner.data.model.SerializableCookie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json
import okhttp3.Cookie

class CookieStorage(
    private val context: Context
) {
    private val key = stringPreferencesKey("cookie_list")
    private val _cookies = MutableStateFlow<List<Cookie>>(emptyList())

    val cookies: StateFlow<List<Cookie>> = _cookies.asStateFlow()

    suspend fun saveCookies(cookies: List<Cookie>) {
        val serializable = cookies.map {
            SerializableCookie(
                it.name,
                it.value,
                it.expiresAt,
                it.domain,
                it.path,
                it.secure,
                it.httpOnly
            )
        }
        val json = Json.encodeToString(serializable)

        context.cookieDataStore.edit {
            it[key] = json
        }

        _cookies.value = cookies
    }

    suspend fun loadCookies(): List<Cookie> {
        val prefs = context.cookieDataStore.data.first()
        val json = prefs[key] ?: return emptyList()
        val stored = Json.decodeFromString<List<SerializableCookie>>(json)

        val list = stored.map {
            Cookie.Builder()
                .name(it.name)
                .value(it.value)
                .domain(it.domain)
                .path(it.path)
                .expiresAt(it.expiresAt)
                .apply { if (it.secure) secure() }
                .apply { if (it.httpOnly) httpOnly() }
                .build()
        }

        _cookies.value = list
        return list
    }

    suspend fun clearCookies() {
        context.cookieDataStore.edit { prefs ->
            prefs.clear()
        }
        _cookies.value = emptyList()
    }
}