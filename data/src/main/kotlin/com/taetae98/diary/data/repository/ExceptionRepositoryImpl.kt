package com.taetae98.diary.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.taetae98.diary.data.datasource.ExceptionRoomDataSource
import com.taetae98.diary.domain.model.exception.ExceptionEntity
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.feature.common.util.getDefaultName
import javax.inject.Inject

class ExceptionRepositoryImpl @Inject constructor(
    private val exceptionRoomDataSource: ExceptionRoomDataSource
) : ExceptionRepository {
    override suspend fun insert(throwable: Throwable): Long {
        Log.e(ExceptionRepositoryImpl::class.getDefaultName(), "Error", throwable)
        return exceptionRoomDataSource.insert(ExceptionEntity(throwable))
    }

    override suspend fun insert(collection: Collection<ExceptionEntity>) = exceptionRoomDataSource.insert(collection)

    override fun pagingAll() = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 300
        ),
    ) {
        exceptionRoomDataSource.pagingAll()
    }.flow

    override suspend fun findAll() = exceptionRoomDataSource.findAll()

    override suspend fun findById(id: Long) = exceptionRoomDataSource.findById(id)

    override suspend fun deleteById(id: Long) = exceptionRoomDataSource.deleteById(id)

    override suspend fun deleteAll() = exceptionRoomDataSource.deleteAll()
}