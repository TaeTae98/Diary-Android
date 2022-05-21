package com.taetae98.diary.app.receiver

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.TaskStackBuilder
import com.taetae98.diary.app.service.RunOnUnlockService
import com.taetae98.diary.feature.common.Const

class RunOnUnlockReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_MY_PACKAGE_REPLACED, Intent.ACTION_BOOT_COMPLETED -> onInit(context)
            Intent.ACTION_SCREEN_OFF -> onScreenOff(context)
        }
    }

    private fun onInit(context: Context) {
        Intent(context, RunOnUnlockService::class.java).also {
            context.startService(it)
        }
    }

    private fun onScreenOff(context: Context) {
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse(Const.MAIN_APP_DEEP_LINK)
        ).also {
            TaskStackBuilder.create(context)
                .addNextIntent(it)
                .getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
                ?.send()
        }
    }
}