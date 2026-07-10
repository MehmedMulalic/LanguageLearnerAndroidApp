package com.mmulalic.languagelearner.data.remote

import android.content.Context
import com.mmulalic.languagelearner.BuildConfig
import com.mmulalic.languagelearner.data.SessionExpiredAuthenticator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideCookieStorage(
        @ApplicationContext context: Context
    ): CookieStorage {
        return DataStoreCookieStorage(context)
    }

    @Provides
    @Singleton
    fun provideCookieJar(
        storage: CookieStorage,
        @ApplicationScope scope: CoroutineScope
    ): PersistentCookieJar {
        return PersistentCookieJar(storage, scope)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        cookieJar: PersistentCookieJar,
        sessionExpiredAuthenticator: SessionExpiredAuthenticator
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .cookieJar(cookieJar)
            .authenticator(sessionExpiredAuthenticator)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(
        okHttpClient: OkHttpClient
    ): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://llaapi.zejdkrek.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}