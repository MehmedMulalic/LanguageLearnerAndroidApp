package com.mmulalic.languagelearner.ui.main.profile.personalization

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmulalic.languagelearner.notifications.ReminderScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationPreference: NotificationPreference,
    @ApplicationContext private val context: Context
) : ViewModel() {
    val notificationsEnabled: StateFlow<Boolean> = notificationPreference.notificationsEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    fun setNotificationsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            notificationPreference.setNotificationsEnabled(enabled)

            if (enabled) {
                ReminderScheduler.scheduleNextReminder(context)
            } else {
                ReminderScheduler.cancelReminder(context)
            }
        }
    }
}
