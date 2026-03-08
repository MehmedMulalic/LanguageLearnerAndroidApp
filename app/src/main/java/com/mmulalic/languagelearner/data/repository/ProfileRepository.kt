package com.mmulalic.languagelearner.data.repository

import android.util.Log
import com.mmulalic.languagelearner.data.remote.ApiService
import com.mmulalic.languagelearner.data.remote.CookieStorage
import kotlinx.coroutines.flow.Flow
import okhttp3.Cookie
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val api: ApiService,
    private val cookieStorage: CookieStorage
){
    val cookies: Flow<List<Cookie>> = cookieStorage.cookies

    suspend fun logout() {
        try {
            Log.d("ProfileRepository", "Attempting logout...")
            api.deleteSignout()
            cookieStorage.clearCookies()
        } catch (e: Exception) {
            Log.e("ProfileRepository", "Logout failed", e)
            throw e
        }
    }
}