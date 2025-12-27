package com.example.myapplication.data.repository

import com.example.myapplication.data.remote.ApiService
import retrofit2.HttpException
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun getUser() : String {
        try {
            return api.getUser().username
        } catch (e: HttpException) {
            println("Http error: ${e.message}")
            println("Http error body: ${e.response()?.errorBody()?.string()}")
            return ""
        } catch (e: Exception) {
            println("Error ${e.message}")
            return ""
        }
    }
}