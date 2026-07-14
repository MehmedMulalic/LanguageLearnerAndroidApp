package com.mmulalic.languagelearner.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mmulalic.languagelearner.ui.main.profile.personalization.NotificationPreference
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {
    @Inject lateinit var notificationPreference: NotificationPreference

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return

        if (intent.action != Intent.ACTION_BOOT_COMPLETED) return

        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (notificationPreference.notificationsEnabled.first()) {
                    ReminderScheduler.scheduleNextReminder(context)
                }
            } finally {
                pendingResult.finish()
            }
        }
    }
}