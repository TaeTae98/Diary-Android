package com.taetae98.diary.data.di

import android.content.Context
import androidx.room.Room
import com.taetae98.diary.data.BuildConfig
import com.taetae98.diary.data.DiaryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DiaryDatabaseProvidesSingletonModule {
    @Provides
    @Singleton
    fun providesDiaryDatabase(
        @ApplicationContext
        context: Context
    ) = Room.databaseBuilder(context, DiaryDatabase::class.java, BuildConfig.DIARY_DATABASE_NAME)
        .build()
}