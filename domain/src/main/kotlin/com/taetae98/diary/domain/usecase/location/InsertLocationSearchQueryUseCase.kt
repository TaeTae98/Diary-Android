package com.taetae98.diary.domain.usecase.location

import com.taetae98.diary.domain.model.LocationSearchQueryEntity
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.LocationSearchQueryRepository
import com.taetae98.diary.domain.usecase.SuspendParamUseCase
import javax.inject.Inject

class InsertLocationSearchQueryUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val locationSearchQueryRepository: LocationSearchQueryRepository
) : SuspendParamUseCase<InsertLocationSearchQueryUseCase.Query, Long>(exceptionRepository) {
    @JvmInline
    value class Query(val query: String)

    override suspend fun execute(parameter: Query) = locationSearchQueryRepository.insert(
        LocationSearchQueryEntity(
            query = parameter.query
        )
    )
}