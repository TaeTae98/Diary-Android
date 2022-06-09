package com.taetae98.diary.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.taetae98.diary.data.converter.UriConverter
import com.taetae98.diary.data.datasource.FileRoomDataSource
import com.taetae98.diary.data.datasource.FolderRoomDataSource
import com.taetae98.diary.data.datasource.MemoPlaceRoomDataSource
import com.taetae98.diary.data.datasource.MemoRoomDataSource
import com.taetae98.diary.data.datasource.PlaceRoomDataSource
import com.taetae98.diary.domain.model.file.FileEntity
import com.taetae98.diary.domain.model.file.FolderEntity
import com.taetae98.diary.domain.model.memo.MemoEntity
import com.taetae98.diary.domain.model.memo.MemoPlaceEntity
import com.taetae98.diary.domain.model.place.PlaceEntity

@Database(
    entities = [
        MemoEntity::class, MemoPlaceEntity::class,
        PlaceEntity::class,
        FileEntity::class, FolderEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(
    value = [UriConverter::class]
)
abstract class DiaryDatabase : RoomDatabase() {
    abstract fun memoRoomDataSource(): MemoRoomDataSource
    abstract fun memoPlaceRoomDataSource(): MemoPlaceRoomDataSource

    abstract fun placeRoomDataSource(): PlaceRoomDataSource

    abstract fun fileRoomDataSource(): FileRoomDataSource
    abstract fun folderRoomDataSource(): FolderRoomDataSource
}