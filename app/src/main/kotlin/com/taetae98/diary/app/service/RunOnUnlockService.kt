package com.taetae98.diary.app.service

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import com.taetae98.diary.app.notification.RunOnUnlockNotification
import com.taetae98.diary.app.receiver.RunOnUnlockReceiver
import com.taetae98.diary.domain.repository.SettingRepository
import com.taetae98.diary.feature.common.isFalse
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RunOnUnlockService : Service() {
    private val runOnUnlockReceiver = RunOnUnlockReceiver()

    @Inject
    lateinit var settingRepository: SettingRepository

    @Inject
    lateinit var runOnUnlockNotification: RunOnUnlockNotification

    override fun onBind(intent: Intent): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        registerReceiver(
            runOnUnlockReceiver,
            IntentFilter().apply {
                addAction(Intent.ACTION_SCREEN_OFF)
            }
        )
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        CoroutineScope(Dispatchers.IO).launch {
            when {
                isForegroundService() -> onForegroundService()
                isBackgroundService() -> onBackgroundService()
                else -> stopSelf()
            }
        }

        return START_STICKY_COMPATIBILITY
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(runOnUnlockReceiver)
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

    private suspend fun isForegroundService(): Boolean {
        return settingRepository.isRunOnUnlockEnable().first() &&
                settingRepository.isRunOnUnlockNotificationVisible().first().isFalse()
    }

    private suspend fun isBackgroundService(): Boolean {
        return settingRepository.isRunOnUnlockEnable().first() &&
                settingRepository.isRunOnUnlockNotificationVisible().first()
    }

    companion object {
        private const val NOTIFICATION_ID = -100
    }
}