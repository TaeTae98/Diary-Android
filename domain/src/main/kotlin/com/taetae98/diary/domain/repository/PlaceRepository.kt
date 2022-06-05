package com.taetae98.diary.domain.repository

import androidx.paging.PagingData
import com.taetae98.diary.domain.model.PlaceEntity
import com.taetae98.diary.domain.model.PlaceRelation
import kotlinx.coroutines.flow.Flow

interface PlaceRepository {
    suspend fun findById(id: Long): PlaceEntity
    suspend fun findRelationById(id: Long): PlaceRelation

    suspend fun insert(entity: PlaceEntity): Long
    suspend fun insert(entity: Collection<PlaceEntity>): LongArray

    suspend fun deleteById(id: Long): Int

    fun pagingByTagIds(ids: Collection<Long>): Flow<PagingData<PlaceEntity>>
}