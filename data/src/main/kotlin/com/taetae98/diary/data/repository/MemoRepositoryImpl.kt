package com.taetae98.diary.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.taetae98.diary.data.datasource.MemoRoomDataSource
import com.taetae98.diary.domain.model.MemoEntity
import com.taetae98.diary.domain.repository.MemoRepository
import javax.inject.Inject

class MemoRepositoryImpl @Inject constructor(
    private val memoRoomDataSource: MemoRoomDataSource
) : MemoRepository {
    override suspend fun insert(entity: MemoEntity) = memoRoomDataSource.insert(entity)

    override fun pagingByTagIds(ids: Collection<Int>) = Pager(
        config = PagingConfig(
            pageSize = 20,
            prefetchDistance = 50,
            maxSize = 1000
        )
    ) {
        memoRoomDataSource.findByTagIds()
    }.flow

    override suspend fun findById(id: Int) = memoRoomDataSource.findById(id)

    override suspend fun deleteById(id: Int) = memoRoomDataSource.deleteById(id)
}