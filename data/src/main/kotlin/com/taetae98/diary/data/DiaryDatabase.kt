package com.taetae98.diary.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.taetae98.diary.domain.MemoEntity

@Database(
    entities = [MemoEntity::class],
    version = 1,
    exportSchema = true
)
abstract class DiaryDatabase : RoomDatabase() {
    abstract fun memoRoomDataSource(): MemoRoomDataSource
}