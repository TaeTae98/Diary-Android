package com.taetae98.diary.domain.usecase.place

import com.taetae98.diary.domain.model.PlaceSearchEntity
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.PlaceSearchQueryRepository
import com.taetae98.diary.domain.usecase.SuspendParamUseCase
import javax.inject.Inject

class InsertPlaceSearchQueryUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val placeSearchQueryRepository: PlaceSearchQueryRepository
) : SuspendParamUseCase<InsertPlaceSearchQueryUseCase.Query, Long>(exceptionRepository) {
    @JvmInline
    value class Query(val query: String)

    override suspend fun execute(parameter: Query) = placeSearchQueryRepository.insert(
        PlaceSearchEntity(
            query = parameter.query
        )
    )
}