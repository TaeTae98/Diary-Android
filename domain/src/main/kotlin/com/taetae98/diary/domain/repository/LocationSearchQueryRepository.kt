package com.taetae98.diary.domain.repository

import androidx.paging.PagingData
import com.taetae98.diary.domain.model.LocationSearchQueryEntity
import kotlinx.coroutines.flow.Flow

interface LocationSearchQueryRepository {
    suspend fun findById(id: Int): LocationSearchQueryEntity
    fun pagingAll(): Flow<PagingData<LocationSearchQueryEntity>>

    suspend fun insert(entity: LocationSearchQueryEntity): Long
    suspend fun deleteById(id: Int): Int
}