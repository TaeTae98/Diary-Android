package com.taetae98.diary.app.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.taetae98.diary.app.notification.RunOnUnlockNotification
import com.taetae98.diary.app.receiver.RunOnUnlockReceiver
import com.taetae98.diary.feature.common.getDefaultName
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RunOnUnlockService : Service() {
    private var runOnUnlockReceiver: RunOnUnlockReceiver? = null

    @Inject
    lateinit var runOnUnlockNotification: RunOnUnlockNotification

    override fun onBind(intent: Intent): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        runCatching {
            runOnUnlockReceiver = RunOnUnlockReceiver().also {
                registerReceiver(
                    it,
                    IntentFilter(Intent.ACTION_SCREEN_OFF)
                )
            }
        }.onFailure {
            Log.e("RunOnUnlock", RunOnUnlockService::class.getDefaultName(), it)
            Toast.makeText(this, "Error : ${it.message}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        runCatching {
            if (intent.getBooleanExtra(IS_BACKGROUND, true)) {
                onBackgroundService()
            } else {
                onForegroundService()
            }
        }.onFailure {
            Log.e("RunOnUnlock", RunOnUnlockService::class.getDefaultName(), it)
            Toast.makeText(this, "Error : ${it.message}", Toast.LENGTH_LONG).show()
        }

        return START_REDELIVER_INTENT
    }

    override fun onDestroy() {
        super.onDestroy()
        runCatching {
            runOnUnlockReceiver?.let { unregisterReceiver(it) }
        }.onFailure {
            Log.e("RunOnUnlock", RunOnUnlockService::class.getDefaultName(), it)
            Toast.makeText(this, "Error : ${it.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun onBackgroundService() {
        stopForeground(true)
    }

    private fun onForegroundService() {
        runOnUnlockNotification.createNotificationChannel()
        startForeground(
            NOTIFICATION_ID,
            runOnUnlockNotification.createNotification()
        )
    }

    companion object {
        private const val NOTIFICATION_ID = -100
        private const val IS_BACKGROUND = "IS_BACKGROUND"

        fun getIntent(context: Context, isBackground: Boolean = false) = Intent(
            context, RunOnUnlockService::class.java
        ).putExtra(IS_BACKGROUND, isBackground)
    }
}