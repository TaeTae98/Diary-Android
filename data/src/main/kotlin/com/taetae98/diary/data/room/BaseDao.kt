package com.taetae98.diary.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
interface BaseDao<T: Any> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: Collection<T>): LongArray

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg entity: T)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(entity: T): Int

    @Delete
    suspend fun delete(entity: T): Int
}