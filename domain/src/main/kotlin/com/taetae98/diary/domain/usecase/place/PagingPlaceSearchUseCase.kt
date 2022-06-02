package com.taetae98.diary.domain.usecase.place

import androidx.paging.PagingData
import com.taetae98.diary.domain.model.PlaceEntity
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.PlaceSearchRepository
import com.taetae98.diary.domain.usecase.ParamUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class PagingPlaceSearchUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val placeSearchRepository: PlaceSearchRepository
) : ParamUseCase<PagingPlaceSearchUseCase.Query, Flow<PagingData<PlaceEntity>>>(exceptionRepository) {
    data class Query(
        val query: String
    )

    override fun execute(parameter: Query) = placeSearchRepository.pagingByMapTypeAndQuery(
        query = parameter.query
    )
}