package com.taetae98.diary.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RoomDataSourceViewModelModule {
    @Provides
    fun providesMemoRoomDataSource(
        diaryDatabase: DiaryDatabase
    ) = diaryDatabase.memoRoomDataSource()
}