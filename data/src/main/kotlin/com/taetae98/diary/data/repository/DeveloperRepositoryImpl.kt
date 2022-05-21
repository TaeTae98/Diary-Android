package com.taetae98.diary.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.taetae98.diary.data.datasource.DeveloperDataStoreDataSource
import com.taetae98.diary.domain.repository.DeveloperRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.map

class DeveloperRepositoryImpl @Inject constructor(
    @DeveloperDataStoreDataSource
    private val developerDataStoreDataSource: DataStore<Preferences>,
) : DeveloperRepository {
    override fun isDeveloperModeEnable() = developerDataStoreDataSource.data.map {
        it[getIsDeveloperModeEnableKey()] ?: false
    }

    override suspend fun setIsDeveloperModeEnable(isEnable: Boolean) {
        developerDataStoreDataSource.edit {
            it[getIsDeveloperModeEnableKey()] = isEnable
        }
    }

    override fun getDeveloperModePassword() = developerDataStoreDataSource.data.map {
        it[getDeveloperModePasswordKey()]
    }

    override suspend fun setDeveloperModePassword(password: String?) {
        developerDataStoreDataSource.edit {
            if (password == null) {
                it.remove(getDeveloperModePasswordKey())
            } else {
                it[getDeveloperModePasswordKey()] = password
            }
        }
    }

    companion object {
        private fun getIsDeveloperModeEnableKey() =
            booleanPreferencesKey("IS_DEVELOPER_MODE_ENABLE")

        private fun getDeveloperModePasswordKey() = stringPreferencesKey("DEVELOPER_MODE_PASSWORD_KEY")
    }
}