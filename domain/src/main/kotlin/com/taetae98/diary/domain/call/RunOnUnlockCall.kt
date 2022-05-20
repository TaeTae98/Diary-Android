package com.taetae98.diary.domain.call

interface RunOnUnlockCall {
    fun stopService()
    fun startForegroundService()
    fun startBackgroundService()
}