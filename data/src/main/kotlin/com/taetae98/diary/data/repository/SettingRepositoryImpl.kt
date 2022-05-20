package com.taetae98.diary.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.taetae98.diary.data.datasource.SettingDataStoreDataSource
import com.taetae98.diary.domain.repository.SettingRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.map

class SettingRepositoryImpl @Inject constructor(
    @SettingDataStoreDataSource
    private val settingDataStoreDataSource: DataStore<Preferences>,
) : SettingRepository {
    override fun isRunOnUnlock() = settingDataStoreDataSource.data.map {
        it[getIsRunOnUnlockKey()] ?: false
    }

    override suspend fun setIsRunOnUnlock(value: Boolean) {
        settingDataStoreDataSource.edit {
            it[getIsRunOnUnlockKey()] = value
        }
    }

    override fun isRunOnUnlockOptimized() = settingDataStoreDataSource.data.map {
        (it[getIsRunOnUnlockOptimizedKey()] ?: false)
    }

    override suspend fun setIsRunOnUnlockOptimized(value: Boolean) {
        settingDataStoreDataSource.edit {
            it[getIsRunOnUnlockOptimizedKey()] = value
        }
    }

    companion object {
        private fun getIsRunOnUnlockKey() = booleanPreferencesKey("IS_RUN_ON_UNLOCK")
        private fun getIsRunOnUnlockOptimizedKey() = booleanPreferencesKey("IS_RUN_ON_UNLOCK_OPTIMIZED")
    }
}