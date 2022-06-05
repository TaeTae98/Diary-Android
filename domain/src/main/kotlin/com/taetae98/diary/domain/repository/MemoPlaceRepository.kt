package com.taetae98.diary.domain.repository

import com.taetae98.diary.domain.model.memo.MemoPlaceEntity

interface MemoPlaceRepository {
    suspend fun insert(entity: Collection<MemoPlaceEntity>): LongArray
}