package com.taetae98.diary.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.taetae98.diary.domain.MemoEntity
import com.taetae98.diary.feature.base.BaseDao

@Dao
interface MemoRoomDataSource : BaseDao<MemoEntity> {
    @Query("""
        SELECT * 
        FROM MemoEntity
    """)
    fun findByTagIds(): PagingSource<Int, MemoEntity>
}