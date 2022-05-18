package com.taetae98.diary.data

import com.taetae98.diary.domain.MemoEntity
import com.taetae98.diary.domain.MemoRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class MemoRepositoryImpl @Inject constructor(
    private val memoRoomDataSource: MemoRoomDataSource
) : MemoRepository {
    override suspend fun insert(entity: MemoEntity) = memoRoomDataSource.insert(entity)
}