package com.taetae98.diary.domain.usecase.place

import com.taetae98.diary.domain.model.place.PlaceEntity
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.PlaceRepository
import com.taetae98.diary.domain.usecase.SuspendParamUseCase
import javax.inject.Inject

class InsertPlaceUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val placeRepository: PlaceRepository
) : SuspendParamUseCase<PlaceEntity, Long>(exceptionRepository) {
    override suspend fun execute(parameter: PlaceEntity) = placeRepository.insert(parameter)
}