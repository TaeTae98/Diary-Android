package com.taetae98.diary.data.datasource

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.taetae98.diary.data.room.BaseDao
import com.taetae98.diary.domain.model.memo.MemoEntity
import com.taetae98.diary.domain.model.memo.MemoRelation

@Dao
interface MemoRoomDataSource : BaseDao<MemoEntity> {
    @Query("SELECT * FROM MemoEntity")
    fun findByTagIds(): PagingSource<Int, MemoEntity>

    @Transaction
    @Query("SELECT * FROM MemoEntity WHERE id = :id")
    suspend fun findRelationById(id: Long): MemoRelation?

    @Query("DELETE FROM MemoEntity WHERE id = :id")
    suspend fun deleteById(id: Long): Int
}