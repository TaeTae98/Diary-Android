package com.taetae98.diary.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.taetae98.diary.data.datasource.MemoRoomDataSource
import com.taetae98.diary.domain.model.MemoEntity

@Database(
    entities = [MemoEntity::class],
    version = 1,
    exportSchema = true
)
abstract class DiaryDatabase : RoomDatabase() {
    abstract fun memoRoomDataSource(): MemoRoomDataSource
}