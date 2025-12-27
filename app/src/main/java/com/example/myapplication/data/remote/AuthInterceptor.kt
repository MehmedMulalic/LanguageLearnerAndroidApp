package com.example.myapplication.data.remote

import android.util.Log
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenStore: TokenStore
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val token = runBlocking {
            tokenStore.token.first()
        }

        val authenticatedRequest = if (token != null && token != "errorCredentials") {
            Log.d("AuthInterceptor", "Adding Authorization header")
            request
                .newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            Log.d("AuthInterceptor", "No valid token, proceeding without auth")
            request
        }

        return chain.proceed(authenticatedRequest)
    }
}