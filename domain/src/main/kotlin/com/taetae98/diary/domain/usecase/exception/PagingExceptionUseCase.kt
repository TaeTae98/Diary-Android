package com.taetae98.diary.domain.usecase.exception

import androidx.paging.PagingData
import com.taetae98.diary.domain.model.ExceptionEntity
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.usecase.UseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class PagingExceptionUseCase @Inject constructor(
    private val exceptionLogRepository: ExceptionRepository
) : UseCase<Flow<PagingData<ExceptionEntity>>>(exceptionLogRepository) {
    override fun execute() = exceptionLogRepository.pagingAll()
}