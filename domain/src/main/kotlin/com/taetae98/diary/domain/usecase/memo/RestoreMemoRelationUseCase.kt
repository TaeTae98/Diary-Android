package com.taetae98.diary.domain.usecase.memo

import com.taetae98.diary.domain.model.table.MemoPlaceEntity
import com.taetae98.diary.domain.model.memo.MemoRelation
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.MemoPlaceRepository
import com.taetae98.diary.domain.repository.MemoRepository
import com.taetae98.diary.domain.usecase.SuspendParamUseCase
import javax.inject.Inject

class RestoreMemoRelationUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val memoRepository: MemoRepository,
    private val memoPlaceRepository: MemoPlaceRepository,
) : SuspendParamUseCase<MemoRelation, Long>(exceptionRepository) {
    override suspend fun execute(parameter: MemoRelation) = memoRepository
        .insert(parameter.memo)
        .also { memoId ->
            parameter.place.map {
                MemoPlaceEntity(
                    memoId = memoId,
                    placeId = it.id
                )
            }.also {
                memoPlaceRepository.insert(it)
            }
        }
}