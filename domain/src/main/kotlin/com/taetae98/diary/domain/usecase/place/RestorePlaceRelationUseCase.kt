package com.taetae98.diary.domain.usecase.place

import com.taetae98.diary.domain.model.place.PlaceRelation
import com.taetae98.diary.domain.model.table.MemoPlaceEntity
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.MemoPlaceRepository
import com.taetae98.diary.domain.repository.PlaceRepository
import com.taetae98.diary.domain.usecase.SuspendParamUseCase
import javax.inject.Inject

class RestorePlaceRelationUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val placeRepository: PlaceRepository,
    private val memoPlaceRepository: MemoPlaceRepository
) : SuspendParamUseCase<PlaceRelation, Long>(exceptionRepository) {
    override suspend fun execute(parameter: PlaceRelation) = placeRepository
        .insert(parameter.place)
        .also { placeId ->
            parameter.memo.map {
                MemoPlaceEntity(
                    memoId = it.id,
                    placeId = placeId
                )
            }.also {
                memoPlaceRepository.insert(it)
            }
        }
}