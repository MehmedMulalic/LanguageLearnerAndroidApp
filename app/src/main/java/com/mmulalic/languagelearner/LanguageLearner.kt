package com.mmulalic.languagelearner

import android.app.Application
import com.mmulalic.languagelearner.data.remote.ApplicationScope
import com.mmulalic.languagelearner.data.remote.PersistentCookieJar
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class LanguageLearner @Inject constructor(
    var cookieJar: PersistentCookieJar,
    @param:ApplicationScope private val scope: CoroutineScope
) : Application() {
    override fun onCreate() {
        super.onCreate()
        scope.launch { cookieJar.initialize() }
    }
}