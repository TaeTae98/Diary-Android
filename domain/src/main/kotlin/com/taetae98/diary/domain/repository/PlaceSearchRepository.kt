package com.taetae98.diary.domain.repository

import androidx.paging.PagingData
import com.taetae98.diary.domain.model.PlaceEntity
import kotlinx.coroutines.flow.Flow

interface PlaceSearchRepository {
    fun pagingByMapTypeAndQuery(query: String): Flow<PagingData<PlaceEntity>>
}