package com.taetae98.diary.data.datasource

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.taetae98.diary.feature.common.util.getDefaultName
import javax.inject.Qualifier

@Qualifier
annotation class SettingDataStoreDataSource {
    companion object {
        val Context.SettingDataStoreDataSource by preferencesDataStore(
            name = SettingDataStoreDataSource::class.getDefaultName()
        )
    }
}