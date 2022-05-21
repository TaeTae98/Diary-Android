package com.taetae98.diary.data.datasource

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.taetae98.diary.feature.common.getDefaultName
import javax.inject.Qualifier

@Qualifier
annotation class DeveloperDataStoreDataSource {
    companion object {
        val Context.DeveloperDataStoreDataSource by preferencesDataStore(
            name = DeveloperDataStoreDataSource::class.getDefaultName()
        )
    }
}