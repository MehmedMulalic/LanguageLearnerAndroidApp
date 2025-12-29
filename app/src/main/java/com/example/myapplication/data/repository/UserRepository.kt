package com.example.myapplication.data.repository

import android.util.Log
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
            Log.d("UserRepository", "Http error: ${e.message}")
            Log.d("UserRepository", "Http error body: ${e.response()?.errorBody()?.string()}")
            return ""
        } catch (e: Exception) {
            Log.e("UserRepository", "Error fetching username")
            e.printStackTrace()
            return ""
        }
    }
}