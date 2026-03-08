package com.mmulalic.languagelearner.data.remote

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
        return CookieStorage(context)
    }

    @Provides
    @Singleton
    fun provideCookieJar(
        storage: CookieStorage
    ): PersistentCookieJar {
        return PersistentCookieJar(storage)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(cookieJar: PersistentCookieJar): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .cookieJar(cookieJar)
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