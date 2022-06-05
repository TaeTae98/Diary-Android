package com.taetae98.diary.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.taetae98.diary.data.datasource.MemoPlaceRoomDataSource
import com.taetae98.diary.data.datasource.MemoRoomDataSource
import com.taetae98.diary.data.datasource.PlaceRoomDataSource
import com.taetae98.diary.domain.model.PlaceEntity
import com.taetae98.diary.domain.model.memo.MemoEntity
import com.taetae98.diary.domain.model.memo.MemoPlaceEntity

@Database(
    entities = [MemoEntity::class, PlaceEntity::class, MemoPlaceEntity::class],
    version = 1,
    exportSchema = true
)
abstract class DiaryDatabase : RoomDatabase() {
    abstract fun memoRoomDataSource(): MemoRoomDataSource
    abstract fun placeRoomDataSource(): PlaceRoomDataSource
    abstract fun memoPlaceRoomDataSource(): MemoPlaceRoomDataSource
}