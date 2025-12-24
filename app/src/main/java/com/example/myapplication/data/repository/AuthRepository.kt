package com.example.myapplication.data.repository

import com.example.myapplication.data.model.LoginRequest
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
            val response = api.postLogin(LoginRequest(username, password))
            tokenStore.saveToken(response.accessToken)
        } catch (e: HttpException) {
            tokenStore.errorCredentialsToken()
        }
    }

    suspend fun logout() {
        tokenStore.clearToken()
    }

    val token: Flow<String?> = tokenStore.token
}
