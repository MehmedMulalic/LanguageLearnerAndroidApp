package com.example.myapplication.data.remote

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.inject.Inject
import androidx.core.content.edit
import com.example.myapplication.data.model.RefreshResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Singleton
import kotlin.text.toByteArray

@Singleton
class TokenStore @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    companion object {
        private const val ACCESS_TOKEN_KEY = "access_token"
        private const val REFRESH_TOKEN_KEY = "refresh_token"
        private const val PREFS_NAME = "auth_prefs"
        private const val ANDROID_KEYSTORE = "AndroidKeyStore"
        private const val KEY_ALIAS = "token_aes_key"
        private const val AES_MODE = "AES/GCM/NoPadding"
        private const val IV_SIZE = 12
        private const val KEY_SIZE = 256
    }

    private val prefs: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME, Context.MODE_PRIVATE
    )
    private val secretKey: SecretKey by lazy { getOrCreateSecretKey() }
    private val _token = MutableStateFlow(readToken())
    val token: StateFlow<String?> = _token.asStateFlow()

    init {
        prefs.registerOnSharedPreferenceChangeListener { _, key ->
            if (key == ACCESS_TOKEN_KEY) {
                val newToken = readToken()
                Log.d("TokenStore", "Prefs changed, updating StateFlow")
                _token.value = newToken
            }
        }
    }

    fun saveTokens(tokens: RefreshResponse) {
        saveAccessToken(tokens.accessToken)
        saveRefreshToken(tokens.refreshToken)
    }

    fun getRefreshToken(): String? {
        val refreshToken = prefs.getString(REFRESH_TOKEN_KEY, null) ?: return null
        return decrypt(refreshToken)
    }

    fun clearTokens() {
        prefs.edit { remove(ACCESS_TOKEN_KEY) }
        prefs.edit { remove(REFRESH_TOKEN_KEY) }
        _token.value = null
    }

    fun errorCredentialsToken() {
        saveAccessToken("errorCredentials")
    }

    private fun saveAccessToken(token: String) {
        try {
            prefs.edit {
                putString(ACCESS_TOKEN_KEY, encrypt(token))
            }
            _token.value = token
            Log.d("TokenStore", "Access Token saved")
        } catch (e: Exception) {
            Log.e("TokenStore", "Error saving refresh token", e)
            e.printStackTrace()
        }
    }

    private fun saveRefreshToken(token: String) {
        try {
            prefs.edit {
                putString(REFRESH_TOKEN_KEY, encrypt(token))
            }
            Log.d("TokenStore", "Refresh Token saved")
        } catch (e: Exception) {
            Log.e("TokenStore", "Error saving refresh token")
            e.printStackTrace()
        }
    }

    private fun encrypt(value: String): String {
        val cipher = Cipher.getInstance(AES_MODE)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val iv = cipher.iv
        val encrypted = cipher.doFinal(value.toByteArray())
        val combined = iv + encrypted

        return Base64.encodeToString(combined, Base64.DEFAULT)
    }

    private fun decrypt(encoded: String): String {
        val combined = Base64.decode(encoded, Base64.DEFAULT)
        val iv = combined.copyOfRange(0, IV_SIZE)
        val encrypted = combined.copyOfRange(IV_SIZE, combined.size)
        val cipher = Cipher.getInstance(AES_MODE)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, GCMParameterSpec(128, iv))

        return String(cipher.doFinal(encrypted))
    }

    private fun readToken(): String? {
        try {
            val encoded = prefs.getString(ACCESS_TOKEN_KEY, null) ?: return null
            return decrypt(encoded)
        } catch (e: Exception) {
            e.printStackTrace()
            clearTokens()
            return null
        }
    }

    private fun getOrCreateSecretKey(): SecretKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null)

        keyStore.getKey(KEY_ALIAS, null)?.let { return it as SecretKey }

        val keyGenerator = KeyGenerator.getInstance("AES", "AndroidKeyStore")
        keyGenerator.init(
            KeyGenParameterSpec.Builder(
                KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setKeySize(KEY_SIZE)
                .build()
        )
        return keyGenerator.generateKey()
    }
}
