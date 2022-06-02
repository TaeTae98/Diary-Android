package com.taetae98.diary.domain.usecase.place

import com.taetae98.diary.domain.model.PlaceEntity
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.PlaceRepository
import com.taetae98.diary.domain.usecase.SuspendParamUseCase
import javax.inject.Inject

class FindPlaceByIdUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val placeRepository: PlaceRepository,
) : SuspendParamUseCase<FindPlaceByIdUseCase.Id, PlaceEntity>(exceptionRepository) {
    @JvmInline
    value class Id(val id: Long)

    override suspend fun execute(parameter: Id) = placeRepository.findById(parameter.id)
}