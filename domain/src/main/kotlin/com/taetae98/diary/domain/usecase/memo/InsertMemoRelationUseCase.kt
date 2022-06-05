package com.taetae98.diary.domain.usecase.memo

import com.taetae98.diary.domain.model.memo.MemoPlaceEntity
import com.taetae98.diary.domain.model.memo.MemoRelation
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.MemoPlaceRepository
import com.taetae98.diary.domain.repository.MemoRepository
import com.taetae98.diary.domain.repository.PlaceRepository
import com.taetae98.diary.domain.usecase.SuspendParamUseCase
import javax.inject.Inject

class InsertMemoRelationUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val memoRepository: MemoRepository,
    private val placeRepository: PlaceRepository,
    private val memoPlaceRepository: MemoPlaceRepository
) : SuspendParamUseCase<MemoRelation, Long>(exceptionRepository) {
    override suspend fun execute(parameter: MemoRelation) = memoRepository
        .insert(parameter.memo)
        .also { memoId ->
            placeRepository.insert(parameter.place).map { placeId ->
                MemoPlaceEntity(
                    memoId = memoId,
                    placeId = placeId
                )
            }.also {
                memoPlaceRepository.insert(it)
            }
        }
}