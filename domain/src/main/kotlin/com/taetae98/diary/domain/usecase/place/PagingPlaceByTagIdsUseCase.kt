package com.taetae98.diary.domain.usecase.place

import androidx.paging.PagingData
import com.taetae98.diary.domain.model.place.PlaceEntity
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.PlaceRepository
import com.taetae98.diary.domain.usecase.ParamUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class PagingPlaceByTagIdsUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val placeRepository: PlaceRepository
) : ParamUseCase<PagingPlaceByTagIdsUseCase.Ids, Flow<PagingData<PlaceEntity>>>(exceptionRepository) {
    @JvmInline
    value class Ids(val ids: Collection<Long>)

    override fun execute(parameter: Ids) = placeRepository.pagingByTagIds(parameter.ids)
}