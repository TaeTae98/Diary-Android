package com.taetae98.diary.data.datasource

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.taetae98.diary.data.room.BaseDao
import com.taetae98.diary.domain.model.ExceptionEntity

@Dao
interface ExceptionRoomDataSource : BaseDao<ExceptionEntity> {
    @Query("SELECT * FROM ExceptionEntity ORDER BY createdAt DESC")
    fun pagingAll(): PagingSource<Int, ExceptionEntity>

    @Query("SELECT * FROM ExceptionEntity")
    fun findAll(): List<ExceptionEntity>

    @Query("SELECT * FROM ExceptionEntity WHERE id = :id")
    suspend fun findById(id: Int): ExceptionEntity

    @Query("DELETE FROM ExceptionEntity WHERE id = :id")
    suspend fun deleteById(id: Int): Int

    @Query("DELETE FROM ExceptionEntity")
    suspend fun deleteAll(): Int
}