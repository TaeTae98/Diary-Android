package com.taetae98.diary.data.di

import android.content.Context
import com.taetae98.diary.data.datasource.SettingDataStoreDataSource
import com.taetae98.diary.data.datasource.SettingDataStoreDataSource.Companion.SettingDataStoreDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreDataSourceProvidesSingletonModule {
    @Provides
    @Singleton
    @SettingDataStoreDataSource
    fun providesSettingDataStoreDataSource(
        @ApplicationContext
        context: Context
    ) = context.SettingDataStoreDataSource
}