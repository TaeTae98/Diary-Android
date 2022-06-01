package com.taetae98.diary.domain.usecase.place

import com.taetae98.diary.domain.model.PlaceSearchQueryRelation
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.PlaceSearchQueryRepository
import com.taetae98.diary.domain.usecase.SuspendParamUseCase
import javax.inject.Inject

class DeletePlaceSearchQueryUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val placeSearchQueryRepository: PlaceSearchQueryRepository
) : SuspendParamUseCase<DeletePlaceSearchQueryUseCase.Id, PlaceSearchQueryRelation>(exceptionRepository) {
    @JvmInline
    value class Id(val id: Long)

    override suspend fun execute(parameter: Id) = PlaceSearchQueryRelation(
        placeSearchQueryRepository.findById(parameter.id)
    ).also {
        placeSearchQueryRepository.deleteById(it.entity.id)
    }
}