package com.taetae98.diary.domain.repository

import androidx.paging.PagingData
import com.taetae98.diary.domain.model.PlaceEntity
import kotlinx.coroutines.flow.Flow

interface PlaceRepository {
    suspend fun findById(id: Long): PlaceEntity
    suspend fun insert(entity: PlaceEntity): Long

    fun pagingByTagIds(ids: Collection<Long>): Flow<PagingData<PlaceEntity>>
}