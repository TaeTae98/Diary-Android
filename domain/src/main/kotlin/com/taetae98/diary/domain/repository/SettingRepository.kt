package com.taetae98.diary.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    fun isRunOnUnlock(): Flow<Boolean>
    suspend fun setIsRunOnUnlock(value: Boolean)

    fun isRunOnUnlockHideNotification(): Flow<Boolean>
    suspend fun setIsRunOnUnlockHideNotification(value: Boolean)
}