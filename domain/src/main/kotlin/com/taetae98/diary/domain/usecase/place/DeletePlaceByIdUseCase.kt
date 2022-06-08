package com.taetae98.diary.domain.usecase.place

import com.taetae98.diary.domain.model.place.PlaceRelation
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.PlaceRepository
import com.taetae98.diary.domain.usecase.SuspendParamUseCase
import javax.inject.Inject

class DeletePlaceByIdUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val placeRepository: PlaceRepository,
) : SuspendParamUseCase<DeletePlaceByIdUseCase.Id, PlaceRelation>(exceptionRepository) {
    @JvmInline
    value class Id(val id: Long)

    override suspend fun execute(parameter: Id) = placeRepository
        .findRelationById(parameter.id)
        .also {
            placeRepository.deleteById(parameter.id)
        }
}