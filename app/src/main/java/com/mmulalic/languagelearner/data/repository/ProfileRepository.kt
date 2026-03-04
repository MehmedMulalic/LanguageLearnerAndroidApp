package com.mmulalic.languagelearner.data.repository

import android.util.Log
import com.mmulalic.languagelearner.data.remote.TokenStore
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