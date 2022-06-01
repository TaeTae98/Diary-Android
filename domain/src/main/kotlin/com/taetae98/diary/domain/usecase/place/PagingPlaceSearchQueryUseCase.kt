package com.taetae98.diary.domain.usecase.place

import androidx.paging.PagingData
import com.taetae98.diary.domain.model.PlaceSearchEntity
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.PlaceSearchQueryRepository
import com.taetae98.diary.domain.usecase.UseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class PagingPlaceSearchQueryUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val placeSearchQueryRepository: PlaceSearchQueryRepository
) : UseCase<Flow<PagingData<PlaceSearchEntity>>>(exceptionRepository) {
    override fun execute() = placeSearchQueryRepository.pagingAll()
}