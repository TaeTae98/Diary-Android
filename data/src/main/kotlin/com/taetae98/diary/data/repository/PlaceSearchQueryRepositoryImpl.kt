package com.taetae98.diary.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.taetae98.diary.data.datasource.PlaceSearchRoomDataSource
import com.taetae98.diary.domain.model.PlaceSearchEntity
import com.taetae98.diary.domain.repository.PlaceSearchQueryRepository
import javax.inject.Inject

class PlaceSearchQueryRepositoryImpl @Inject constructor(
    private val placeSearchRoomDataSource: PlaceSearchRoomDataSource
) : PlaceSearchQueryRepository {
    override fun pagingAll() = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 1000
        )
    ) {
        placeSearchRoomDataSource.pagingAll()
    }.flow

    override suspend fun findById(id: Long) = placeSearchRoomDataSource.findById(id)

    override suspend fun insert(entity: PlaceSearchEntity) = placeSearchRoomDataSource.insert(entity)

    override suspend fun deleteById(id: Long) = placeSearchRoomDataSource.deleteById(id)
}