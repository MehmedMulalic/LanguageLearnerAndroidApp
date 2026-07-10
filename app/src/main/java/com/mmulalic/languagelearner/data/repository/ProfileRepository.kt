package com.mmulalic.languagelearner.data.repository

import android.util.Log
import com.mmulalic.languagelearner.data.remote.ApiService
import com.mmulalic.languagelearner.data.remote.CookieStorage
import kotlinx.coroutines.flow.Flow
import okhttp3.Cookie
import retrofit2.HttpException
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val api: ApiService,
    private val dataStoreCookieStorage: CookieStorage
){
    val cookies: Flow<List<Cookie>> = dataStoreCookieStorage.cookies

    suspend fun logout() {
        try {
            Log.d("ProfileRepository", "Attempting logout...")
            val response = api.deleteSignout()
            if (!response.isSuccessful) {
                throw HttpException(response)
            }
            dataStoreCookieStorage.clearCookies()
        } catch (e: Exception) {
            Log.e("ProfileRepository", "Logout failed", e)
            throw e
        }
    }
}