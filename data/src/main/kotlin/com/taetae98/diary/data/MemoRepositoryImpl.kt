package com.taetae98.diary.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.taetae98.diary.domain.MemoEntity
import com.taetae98.diary.domain.MemoRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class MemoRepositoryImpl @Inject constructor(
    private val memoRoomDataSource: MemoRoomDataSource
) : MemoRepository {
    override suspend fun insert(entity: MemoEntity) = memoRoomDataSource.insert(entity)

    override fun findByTagIds(ids: Collection<Int>) = Pager(
        config = PagingConfig(
            pageSize = 10,
            prefetchDistance = 20,
            maxSize = 1000
        )
    ) {
        memoRoomDataSource.findByTagIds()
    }.flow
}