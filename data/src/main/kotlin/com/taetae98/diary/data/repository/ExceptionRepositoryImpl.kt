package com.taetae98.diary.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.taetae98.diary.data.datasource.ExceptionRoomDataSource
import com.taetae98.diary.domain.model.ExceptionEntity
import com.taetae98.diary.domain.repository.ExceptionRepository
import javax.inject.Inject

class ExceptionRepositoryImpl @Inject constructor(
    private val exceptionRoomDataSource: ExceptionRoomDataSource
) : ExceptionRepository {
    override suspend fun insert(throwable: Throwable) = exceptionRoomDataSource.insert(ExceptionEntity(throwable))

    override suspend fun insert(collection: Collection<ExceptionEntity>) = exceptionRoomDataSource.insert(collection)

    override fun pagingAll() = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 1000
        ),
    ) {
        exceptionRoomDataSource.pagingAll()
    }.flow

    override suspend fun findAll() = exceptionRoomDataSource.findAll()

    override suspend fun findById(id: Int) = exceptionRoomDataSource.findById(id)

    override suspend fun deleteById(id: Int) = exceptionRoomDataSource.deleteById(id)

    override suspend fun deleteAll() = exceptionRoomDataSource.deleteAll()
}