package com.taetae98.diary.data.room

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface BaseDao<T: Any> {
    @Insert
    suspend fun insert(entity: T): Long
}