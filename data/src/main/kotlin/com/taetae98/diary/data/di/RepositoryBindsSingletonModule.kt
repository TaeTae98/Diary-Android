package com.taetae98.diary.data.di

import com.taetae98.diary.data.repository.ExceptionRepositoryImpl
import com.taetae98.diary.data.repository.SettingRepositoryImpl
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.SettingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBindsSingletonModule {
    @Binds
    @Singleton
    abstract fun bindsSettingRepository(repository: SettingRepositoryImpl): SettingRepository

    @Binds
    @Singleton
    abstract fun bindsLogRepository(repository: ExceptionRepositoryImpl): ExceptionRepository
}