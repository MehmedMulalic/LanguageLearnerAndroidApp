package com.mmulalic.languagelearner.data.remote

import com.mmulalic.languagelearner.data.model.RefreshRequest
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val tokenStore: TokenStore,
    private val apiService: ApiService
) : Authenticator {
    @Synchronized
    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.priorResponse != null) return null

        val refreshToken = tokenStore.getRefreshToken() ?: return null

        val newTokens = try {
            runBlocking {
                apiService.postRefresh(RefreshRequest(refreshToken))
            }
        } catch (e: Exception) {
            tokenStore.clearTokens()
            e.printStackTrace()
            return null
        }

        tokenStore.saveTokens(newTokens)

        return response.request.newBuilder()
            .header("Authorization", "Bearer ${newTokens.accessToken}")
            .build()
    }
}