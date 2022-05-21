package com.taetae98.diary.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    fun isRunOnUnlockEnable(): Flow<Boolean>
    suspend fun setRunOnUnlockEnable(isEnable: Boolean)

    fun isRunOnUnlockNotificationVisible(): Flow<Boolean>
    suspend fun setRunOnUnlockNotificationVisible(isVisible: Boolean)
}