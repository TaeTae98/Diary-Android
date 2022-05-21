package com.taetae98.diary.data.di

import android.content.Context
import com.taetae98.diary.data.datasource.DeveloperDataStoreDataSource
import com.taetae98.diary.data.datasource.DeveloperDataStoreDataSource.Companion.DeveloperDataStoreDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreDataSourceProvidesViewModelModule {
    @Provides
    @Singleton
    @DeveloperDataStoreDataSource
    fun providesDeveloperDataStoreDataSource(
        @ApplicationContext
        context: Context
    ) = context.DeveloperDataStoreDataSource
}