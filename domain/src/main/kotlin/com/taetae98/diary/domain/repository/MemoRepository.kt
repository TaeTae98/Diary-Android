package com.taetae98.diary.domain.repository

import androidx.paging.PagingData
import com.taetae98.diary.domain.model.memo.MemoEntity
import com.taetae98.diary.domain.model.memo.MemoRelation
import kotlinx.coroutines.flow.Flow

interface MemoRepository {
    fun pagingByTagIds(ids: Collection<Long>): Flow<PagingData<MemoEntity>>

    suspend fun findRelationById(id: Long): MemoRelation
    suspend fun insert(entity: MemoEntity): Long
    suspend fun deleteById(id: Long): Int
}