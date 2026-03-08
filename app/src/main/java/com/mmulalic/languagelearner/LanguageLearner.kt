package com.mmulalic.languagelearner

import android.app.Application
import com.mmulalic.languagelearner.data.remote.PersistentCookieJar
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class LanguageLearner : Application() {
    @Inject lateinit var cookieJar: PersistentCookieJar

    override fun onCreate() {
        super.onCreate()

        CoroutineScope(Dispatchers.IO).launch {
            cookieJar.initialize()
        }
    }
}