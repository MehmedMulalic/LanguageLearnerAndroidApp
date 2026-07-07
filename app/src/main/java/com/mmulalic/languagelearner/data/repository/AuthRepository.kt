package com.mmulalic.languagelearner.data.repository

import android.util.Log
import com.google.gson.Gson
import com.mmulalic.languagelearner.data.model.LoginRequest
import com.mmulalic.languagelearner.data.model.LoginResult
import com.mmulalic.languagelearner.data.model.SignupErrorResponse
import com.mmulalic.languagelearner.data.model.SignupRequest
import com.mmulalic.languagelearner.data.model.exceptions.UsernameTakenException
import com.mmulalic.languagelearner.data.remote.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

sealed interface AuthState {
    object Unknown : AuthState
    object Authenticated : AuthState
    object Unauthenticated : AuthState
}

class AuthRepository @Inject constructor(
    private val api: ApiService
) {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Unknown)
    val authState: StateFlow<AuthState> = _authState

    fun setAuthenticated() {
        _authState.value = AuthState.Authenticated
    }

    fun setUnauthenticated() {
        _authState.value = AuthState.Unauthenticated
    }

    suspend fun login(
        username: String,
        password: String
    ): LoginResult {
        return try {
            Log.d("AuthRepository", "Attempting login...")
            api.postLogin(LoginRequest(username, password))
            LoginResult.Success

        } catch (e: IOException) {
            Log.e("AuthRepository", "Login failed - IOException", e)
            LoginResult.Error.NoInternet

        } catch (e: HttpException) {
            Log.e("AuthRepository", "Login failed - HttpException", e)
            when (e.code()) {
                401 -> LoginResult.Error.InvalidCredentials
                in 500..599 -> LoginResult.Error.ServerError
                else -> LoginResult.Error.Unknown
            }

        } catch (e: Exception) {
            Log.e("AuthRepository", "Login failed - Unknown error", e)
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
}
