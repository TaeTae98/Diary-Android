package com.taetae98.diary.app.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import com.taetae98.diary.feature.common.Const
import com.taetae98.diary.feature.common.getDefaultName
import com.taetae98.diary.feature.resource.DrawableResource
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
    ).setSmallIcon(
        DrawableResource.ic_round_bolt_24
    ).setContentTitle(
        context.getString(StringResource.setting_run_on_unlock)
    ).setContentIntent(
        TaskStackBuilder.create(context)
            .addNextIntent(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(Const.MAIN_APP_DEEP_LINK)
                )
            )
            .getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
    ).build()
}