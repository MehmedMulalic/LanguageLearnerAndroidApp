package com.example.myapplication.data.repository

import android.util.Log
import com.example.myapplication.data.model.LoginRequest
import com.example.myapplication.data.model.RefreshResponse
import com.example.myapplication.data.remote.ApiService
import com.example.myapplication.data.remote.TokenStore
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: ApiService,
    private val tokenStore: TokenStore
) {
    suspend fun login(username: String, password: String) {
        try {
            Log.d("AuthRepository", "Attempting login...")
            val response = api.postLogin(LoginRequest(username, password))
            tokenStore.saveTokens(RefreshResponse(response.accessToken, response.refreshToken))
        } catch (e: HttpException) {
            Log.e("AuthRepository", "Login failed with HTTP ${e.code()}", e)
            tokenStore.errorCredentialsToken()
        } catch (e: Exception) {
            Log.e("AuthRepository", "Login failed", e)
            throw e
        }
    }

    fun logout() {
        tokenStore.clearToken()
    }

    fun signup(username: String, password: String) {
        TODO()
    }

    val token: Flow<String?> = tokenStore.token
}
