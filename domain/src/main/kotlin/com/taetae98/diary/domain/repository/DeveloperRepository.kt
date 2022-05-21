package com.taetae98.diary.domain.repository

import kotlinx.coroutines.flow.Flow

interface DeveloperRepository {
    fun isDeveloperModeEnable(): Flow<Boolean>
    suspend fun setIsDeveloperModeEnable(isEnable: Boolean)

    fun getDeveloperModePassword(): Flow<String?>
    suspend fun setDeveloperModePassword(password: String?)
}