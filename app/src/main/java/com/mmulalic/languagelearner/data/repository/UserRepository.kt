package com.mmulalic.languagelearner.data.repository

import android.util.Log
import com.mmulalic.languagelearner.data.model.UserData
import com.mmulalic.languagelearner.data.remote.ApiService
import retrofit2.HttpException
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun getUserData() : UserData {
        try {
            return api.getUserData()
        } catch (e: HttpException) {
            Log.d("UserRepository", "Http error: ${e.message}")
            Log.d("UserRepository", "Http error body: ${e.response()?.errorBody()?.string()}")
            return UserData()
        } catch (e: Exception) {
            Log.e("UserRepository", "Error fetching username")
            e.printStackTrace()
            return UserData()
        }
    }
}