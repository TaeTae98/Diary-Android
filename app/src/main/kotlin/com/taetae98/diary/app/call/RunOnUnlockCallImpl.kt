package com.taetae98.diary.app.call

import android.content.Context
import android.content.Intent
import com.taetae98.diary.app.service.RunOnUnlockService
import com.taetae98.diary.domain.call.RunOnUnlockCall
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RunOnUnlockCallImpl @Inject constructor(
    @ApplicationContext
    private val context: Context
) : RunOnUnlockCall {
    override fun stopService() {
        context.stopService(Intent(context, RunOnUnlockService::class.java))
    }

    override fun startForegroundService() {
        context.startForegroundService(RunOnUnlockService.getIntent(context, false))
    }

    override fun startBackgroundService() {
        context.startService(RunOnUnlockService.getIntent(context, true))
    }
}