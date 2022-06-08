package com.taetae98.diary.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.taetae98.diary.data.datasource.ExceptionRoomDataSource
import com.taetae98.diary.domain.model.exception.ExceptionEntity

@Database(
    entities = [ExceptionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ExceptionDatabase : RoomDatabase() {
    abstract fun exceptionRoomDataSource(): ExceptionRoomDataSource
}