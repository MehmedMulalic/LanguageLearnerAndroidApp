package com.example.myapplication.data.remote

import com.example.myapplication.data.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    suspend fun postLogin(
        @Body request: LoginRequest
    ): LoginResponse

    @GET("auth/me")
    suspend fun getUser(): LoginResponse
}