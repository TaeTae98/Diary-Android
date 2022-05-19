package com.taetae98.diary.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface MemoRepository {
    fun findByTagIds(ids: Collection<Int>): Flow<PagingData<MemoEntity>>
    suspend fun insert(entity: MemoEntity): Long
}