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
    override fun isRunOnUnlockEnable() = settingDataStoreDataSource.data.map {
        it[getIsRunOnUnlockEnableKey()] ?: false
    }

    override suspend fun setRunOnUnlockEnable(isEnable: Boolean) {
        settingDataStoreDataSource.edit {
            it[getIsRunOnUnlockEnableKey()] = isEnable
        }
    }

    override fun isRunOnUnlockNotificationVisible() = settingDataStoreDataSource.data.map {
        (it[getIsRunOnUnlockNotificationVisibleKey()] ?: false)
    }

    override suspend fun setRunOnUnlockNotificationVisible(isVisible: Boolean) {
        settingDataStoreDataSource.edit {
            it[getIsRunOnUnlockNotificationVisibleKey()] = isVisible
        }
    }

    companion object {
        private fun getIsRunOnUnlockEnableKey() = booleanPreferencesKey("IS_RUN_ON_UNLOCK_ENABLE")
        private fun getIsRunOnUnlockNotificationVisibleKey() = booleanPreferencesKey("IS_RUN_ON_UNLOCK_NOTIFICATION_VISIBLE")
    }
}