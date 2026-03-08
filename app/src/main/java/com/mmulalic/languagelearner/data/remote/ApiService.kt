package com.mmulalic.languagelearner.data.remote

import com.mmulalic.languagelearner.data.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("userdisplaydata")
    suspend fun getUser(): LoginResponse

    @POST("signin")
    suspend fun postLogin(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("signup")
    suspend fun postSignup(
        @Body request: SignupRequest
    ): SignupResponse

    @DELETE("signout")
    suspend fun deleteSignout(): Response<Unit>
}
