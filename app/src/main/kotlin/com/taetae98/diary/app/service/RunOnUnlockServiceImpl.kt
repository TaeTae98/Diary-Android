package com.taetae98.diary.app.service

import android.content.Context
import android.content.Intent
import com.taetae98.diary.domain.service.RunOnUnlockService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RunOnUnlockServiceImpl @Inject constructor(
    @ApplicationContext
    private val context: Context
) : RunOnUnlockService {
    override fun stopService() {
        context.stopService(Intent(context, RunOnUnlockService::class.java))
    }

    override fun startService() {
        context.startService(Intent(context, RunOnUnlockService::class.java))
    }
}