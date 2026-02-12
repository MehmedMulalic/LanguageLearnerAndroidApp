package com.example.myapplication.data.repository

import android.util.Log
import com.example.myapplication.data.remote.TokenStore
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val tokenStore: TokenStore
){
    fun logout() {
        try {
            Log.d("ProfileRepository", "Attempting logout...")
            tokenStore.clearTokens()
        } catch (e: Exception) {
            Log.e("ProfileRepository", "Logout failed", e)
            throw e
        }
    }
}