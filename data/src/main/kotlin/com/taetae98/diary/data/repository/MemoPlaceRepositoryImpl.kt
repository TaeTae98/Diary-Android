package com.taetae98.diary.data.repository

import com.taetae98.diary.data.datasource.MemoPlaceRoomDataSource
import com.taetae98.diary.domain.model.memo.MemoPlaceEntity
import com.taetae98.diary.domain.repository.MemoPlaceRepository
import javax.inject.Inject

class MemoPlaceRepositoryImpl @Inject constructor(
    private val memoPlaceRoomDataSource: MemoPlaceRoomDataSource
) : MemoPlaceRepository {
    override suspend fun insert(entity: Collection<MemoPlaceEntity>) = memoPlaceRoomDataSource.insert(entity)
}