package com.taetae98.diary.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.taetae98.diary.domain.BaseDao
import com.taetae98.diary.domain.MemoEntity

@Dao
interface MemoRoomDataSource : BaseDao<MemoEntity> {
    @Query("SELECT * FROM MemoEntity")
    fun findByTagIds(): PagingSource<Int, MemoEntity>

    @Query("SELECT * FROM MemoEntity WHERE id = :id")
    fun findById(id: Int): MemoEntity

    @Query("DELETE FROM MemoEntity WHERE id = :id")
    suspend fun deleteById(id: Int): Int
}