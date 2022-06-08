package com.taetae98.diary.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.taetae98.diary.data.datasource.PlaceRoomDataSource
import com.taetae98.diary.domain.model.place.PlaceEntity
import com.taetae98.diary.domain.model.place.PlaceRelation
import com.taetae98.diary.domain.repository.PlaceRepository
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor(
    private val placeRoomDataSource: PlaceRoomDataSource
) : PlaceRepository {
    override suspend fun findById(id: Long) = placeRoomDataSource.findById(id) ?: PlaceEntity()
    override suspend fun findRelationById(id: Long) = placeRoomDataSource.findRelationById(id) ?: PlaceRelation()

    override suspend fun insert(entity: PlaceEntity) = placeRoomDataSource.insert(entity)

    override suspend fun insert(entity: Collection<PlaceEntity>) = placeRoomDataSource.insert(entity)

    override suspend fun deleteById(id: Long) = placeRoomDataSource.deleteById(id)

    override fun pagingByTagIds(ids: Collection<Long>) = Pager(
        config = PagingConfig(
            pageSize = 20,
        ),
    ) {
        placeRoomDataSource.pagingByTagIds()
    }.flow
}