package com.mmulalic.languagelearner.data.repository

import android.util.Log
import com.mmulalic.languagelearner.data.model.LoginRequest
import com.mmulalic.languagelearner.data.model.SignupRequest
import com.mmulalic.languagelearner.data.remote.ApiService
import com.mmulalic.languagelearner.data.remote.CookieStorage
import kotlinx.coroutines.flow.Flow
import okhttp3.Cookie
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: ApiService,
    cookieStorage: CookieStorage

) {
    val cookies: Flow<List<Cookie>> = cookieStorage.cookies

    suspend fun login(username: String, password: String) {
        try {
            Log.d("AuthRepository", "Attempting login...")
            api.postLogin(LoginRequest(username, password))
        } catch (e: Exception) {
            Log.e("AuthRepository", "Login failed", e)
            throw e
        }
    }

    suspend fun signup(username: String, password: String) {
        try {
            Log.d("AuthRepository", "Attempting signup...")
            api.postSignup(SignupRequest(username, password))
        } catch (e: Exception) {
            Log.e("AuthRepository", "Signup failed", e)
            throw e
        }
    }
}
