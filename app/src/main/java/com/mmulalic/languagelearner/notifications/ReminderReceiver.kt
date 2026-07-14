package com.mmulalic.languagelearner.notifications

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class ReminderReceiver : BroadcastReceiver() {
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return

        val notification = NotificationCompat.Builder(
            context,
            "reminders"
        )
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Daily Language Learner Reminder")
            .setContentText("Don't forget your daily lessons!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat
            .from(context)
            .notify(1, notification)

        ReminderScheduler.scheduleNextReminder(context)
    }
}