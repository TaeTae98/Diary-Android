package com.taetae98.diary.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.taetae98.diary.data.datasource.LocationSearchQueryRoomDataSource
import com.taetae98.diary.domain.model.LocationSearchQueryEntity
import com.taetae98.diary.domain.repository.LocationSearchQueryRepository
import javax.inject.Inject

class LocationSearchQueryRepositoryImpl @Inject constructor(
    private val locationSearchQueryRoomDataSource: LocationSearchQueryRoomDataSource
) : LocationSearchQueryRepository {
    override fun pagingAll() = Pager(
        config = PagingConfig(
            pageSize = 20
        )
    ) {
        locationSearchQueryRoomDataSource.pagingAll()
    }.flow

    override suspend fun findById(id: Int) = locationSearchQueryRoomDataSource.findById(id)

    override suspend fun insert(entity: LocationSearchQueryEntity) = locationSearchQueryRoomDataSource.insert(entity)

    override suspend fun deleteById(id: Int) = locationSearchQueryRoomDataSource.deleteById(id)
}