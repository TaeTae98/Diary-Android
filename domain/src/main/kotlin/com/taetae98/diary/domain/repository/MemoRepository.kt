package com.taetae98.diary.domain.repository

import androidx.paging.PagingData
import com.taetae98.diary.domain.model.MemoEntity
import kotlinx.coroutines.flow.Flow

interface MemoRepository {
    fun pagingByTagIds(ids: Collection<Int>): Flow<PagingData<MemoEntity>>

    suspend fun findById(id: Int): MemoEntity
    suspend fun insert(entity: MemoEntity): Long
    suspend fun deleteById(id: Int): Int
}