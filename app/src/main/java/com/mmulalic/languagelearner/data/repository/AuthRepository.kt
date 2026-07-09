package com.mmulalic.languagelearner.data.repository

import android.util.Log
import com.google.gson.Gson
import com.mmulalic.languagelearner.data.model.LoginRequest
import com.mmulalic.languagelearner.data.model.LoginResult
import com.mmulalic.languagelearner.data.model.SignupErrorResponse
import com.mmulalic.languagelearner.data.model.SignupRequest
import com.mmulalic.languagelearner.data.model.exceptions.UsernameTakenException
import com.mmulalic.languagelearner.data.remote.ApiService
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: ApiService,
    private val sessionManager: SessionManager
) {
    val authState: StateFlow<AuthState> = sessionManager.authState
    val sessionEvents: SharedFlow<SessionEvent> = sessionManager.sessionEvents

    suspend fun login(
        username: String,
        password: String
    ): LoginResult {
        return try {
            Log.d("AuthRepository", "Attempting login...")
            api.postLogin(LoginRequest(username, password))
            sessionManager.setAuthenticated()
            LoginResult.Success

        } catch (e: IOException) {
            Log.e("AuthRepository", "Login failed - IOException", e)
            sessionManager.setUnauthenticated()
            LoginResult.Error.NoInternet

        } catch (e: HttpException) {
            Log.e("AuthRepository", "Login failed - HttpException", e)
            sessionManager.setUnauthenticated()
            when (e.code()) {
                422 -> LoginResult.Error.InvalidCredentials
                in 500..599 -> LoginResult.Error.ServerError
                else -> LoginResult.Error.Unknown
            }

        } catch (e: Exception) {
            Log.e("AuthRepository", "Login failed - Unknown error", e)
            sessionManager.setUnauthenticated()
            LoginResult.Error.Unknown
        }
    }

    suspend fun signup(username: String, password: String) {
        try {
            Log.d("AuthRepository", "Attempting signup...")
            api.postSignup(SignupRequest(username, password))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()

            val error = runCatching {
                Gson().fromJson(errorBody, SignupErrorResponse::class.java)
            }.getOrNull()

            if (error?.infoMessage == "Username is taken!") {
                throw UsernameTakenException()
            }

            throw e
        }
        catch (e: Exception) {
            Log.e("AuthRepository", "Signup failed", e)
            throw e
        }
    }

    fun logout() {
        sessionManager.setUnauthenticated()
    }
}
