package com.taetae98.diary.domain

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface BaseDao<T: Any> {
    @Insert
    suspend fun insert(entity: T): Long
}