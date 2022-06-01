package com.taetae98.diary.data.di

import com.taetae98.diary.data.database.DiaryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class RoomDataSourceProvidesViewModelModule {
    @Provides
    @ViewModelScoped
    fun providesMemoRoomDataSource(
        diaryDatabase: DiaryDatabase
    ) = diaryDatabase.memoRoomDataSource()

    @Provides
    @ViewModelScoped
    fun providesPlaceSearchQueryRoomDataStore(
        diaryDatabase: DiaryDatabase
    ) = diaryDatabase.placeSearchQueryRoomDataSource()
}