package com.taetae98.diary.app.notification

import android.content.Context
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.taetae98.diary.feature.common.getDefaultName
import com.taetae98.diary.feature.resource.StringResource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RunOnUnlockNotification @Inject constructor(
    @ApplicationContext
    private val context: Context
) {
    private val notificationManager by lazy { NotificationManagerCompat.from(context) }

    fun createNotificationChannel() = NotificationChannelCompat.Builder(
        RunOnUnlockNotification::class.getDefaultName(),
        NotificationManagerCompat.IMPORTANCE_MIN
    ).setName(
        context.getString(StringResource.setting_run_on_unlock)
    ).setShowBadge(
        false
    ).build().also {
        notificationManager.createNotificationChannel(it)
    }

    fun createNotification() = NotificationCompat.Builder(
        context,
        RunOnUnlockNotification::class.getDefaultName()
    ).build()
}