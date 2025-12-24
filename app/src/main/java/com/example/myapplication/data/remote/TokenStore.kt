package com.example.myapplication.data.remote

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.inject.Inject
import androidx.core.content.edit
import javax.crypto.spec.GCMParameterSpec

class TokenStore @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    companion object {
        private const val TOKEN_KEY = "auth_token"
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

    val token: Flow<String?> = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == TOKEN_KEY) {
                trySend(readToken())
            }
        }
        prefs.registerOnSharedPreferenceChangeListener(listener)
        trySend(readToken())
        awaitClose { prefs.unregisterOnSharedPreferenceChangeListener(listener) }
    }

    fun saveToken(token: String) {
        val cipher = Cipher.getInstance(AES_MODE)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val iv = cipher.iv
        val encrypted = cipher.doFinal(token.toByteArray())
        val combined = iv + encrypted
        prefs.edit { putString(TOKEN_KEY, Base64.encodeToString(combined, Base64.DEFAULT)) }
    }

    fun clearToken() {
        prefs.edit { remove(TOKEN_KEY) }
    }

    fun errorCredentialsToken() {
        saveToken("errorCredentials")
    }

    private fun readToken(): String? {
        val encoded = prefs.getString(TOKEN_KEY, null) ?: return null
        val combined = Base64.decode(encoded, Base64.DEFAULT)
        val iv = combined.copyOfRange(0, IV_SIZE)
        val encrypted = combined.copyOfRange(IV_SIZE, combined.size)
        val cipher = Cipher.getInstance(AES_MODE)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, GCMParameterSpec(128, iv))
        return String(cipher.doFinal(encrypted))
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
