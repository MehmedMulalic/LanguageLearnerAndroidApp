package com.mmulalic.languagelearner

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.mmulalic.languagelearner.data.remote.ApplicationScope
import com.mmulalic.languagelearner.data.remote.PersistentCookieJar
import com.mmulalic.languagelearner.data.repository.SessionManager
import com.mmulalic.languagelearner.notifications.ReminderScheduler
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class LanguageLearner: Application() {
    @Inject lateinit var sessionManager: SessionManager
    @Inject lateinit var cookieJar: PersistentCookieJar
    @Inject @ApplicationScope lateinit var scope: CoroutineScope

    override fun onCreate() {
        super.onCreate()

        scope.launch {
            cookieJar.initialize()
            resolveInitialAuthState()
            createNotificationChannel()
        }

        ReminderScheduler.scheduleNextReminder(this)
    }

    private fun resolveInitialAuthState() {
        if (cookieJar.hasValidSession()) {
            sessionManager.setAuthenticated()
        } else {
            sessionManager.setUnauthenticated()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "reminders",
                "Reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
}