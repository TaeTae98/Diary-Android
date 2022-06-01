package com.taetae98.diary.domain.repository

import androidx.paging.PagingData
import com.taetae98.diary.domain.model.PlaceSearchEntity
import kotlinx.coroutines.flow.Flow

interface PlaceSearchQueryRepository {
    suspend fun findById(id: Long): PlaceSearchEntity
    fun pagingAll(): Flow<PagingData<PlaceSearchEntity>>

    suspend fun insert(entity: PlaceSearchEntity): Long
    suspend fun deleteById(id: Long): Int
}