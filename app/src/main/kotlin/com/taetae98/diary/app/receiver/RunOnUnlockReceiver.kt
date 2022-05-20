package com.taetae98.diary.app.receiver

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.app.TaskStackBuilder
import com.taetae98.diary.feature.common.Const
import com.taetae98.diary.feature.common.getDefaultName

class RunOnUnlockReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        runCatching {
            Log.d("RunOnUnlock", "Receiver : ${intent.action}")
            when (intent.action) {
                Intent.ACTION_SCREEN_OFF -> onScreenOff(context)
            }
        }.onFailure {
            Toast.makeText(context, "Error : $it", Toast.LENGTH_LONG).show()
            Log.e("RunOnUnlock", RunOnUnlockReceiver::class.getDefaultName(), it)
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