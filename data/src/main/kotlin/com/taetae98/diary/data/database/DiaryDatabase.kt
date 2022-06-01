package com.taetae98.diary.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.taetae98.diary.data.datasource.LocationSearchQueryRoomDataSource
import com.taetae98.diary.data.datasource.MemoRoomDataSource
import com.taetae98.diary.domain.model.LocationSearchQueryEntity
import com.taetae98.diary.domain.model.MemoEntity

@Database(
    entities = [MemoEntity::class, LocationSearchQueryEntity::class],
    version = 1,
    exportSchema = true
)
abstract class DiaryDatabase : RoomDatabase() {
    abstract fun memoRoomDataSource(): MemoRoomDataSource
    abstract fun locationSearchQueryRoomDataSource(): LocationSearchQueryRoomDataSource
}