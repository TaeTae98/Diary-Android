package com.taetae98.diary.domain.usecase.location

import com.taetae98.diary.domain.model.LocationSearchQueryRelation
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.LocationSearchQueryRepository
import com.taetae98.diary.domain.usecase.SuspendParamUseCase
import javax.inject.Inject

class DeleteLocationSearchQueryUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val locationSearchQueryRepository: LocationSearchQueryRepository
) : SuspendParamUseCase<DeleteLocationSearchQueryUseCase.Id, LocationSearchQueryRelation>(exceptionRepository) {
    @JvmInline
    value class Id(val id: Int)

    override suspend fun execute(parameter: Id) = LocationSearchQueryRelation(
        locationSearchQueryRepository.findById(parameter.id)
    ).also {
        locationSearchQueryRepository.deleteById(it.entity.id)
    }
}