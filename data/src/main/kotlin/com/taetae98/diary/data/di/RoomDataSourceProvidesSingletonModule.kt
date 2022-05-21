package com.taetae98.diary.data.di

import com.taetae98.diary.data.database.ExceptionDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomDataSourceProvidesSingletonModule {
    @Provides
    @Singleton
    fun providesExceptionRoomDataSource(
        exceptionDatabase: ExceptionDatabase
    ) = exceptionDatabase.exceptionRoomDataSource()
}