package com.taetae98.diary.domain.usecase.location

import androidx.paging.PagingData
import com.taetae98.diary.domain.model.LocationSearchQueryEntity
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.LocationSearchQueryRepository
import com.taetae98.diary.domain.usecase.UseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class PagingLocationSearchQueryUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val locationSearchQueryRepository: LocationSearchQueryRepository
) : UseCase<Flow<PagingData<LocationSearchQueryEntity>>>(exceptionRepository) {
    override fun execute() = locationSearchQueryRepository.pagingAll()
}