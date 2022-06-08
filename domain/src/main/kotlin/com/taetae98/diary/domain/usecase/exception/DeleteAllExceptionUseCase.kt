package com.taetae98.diary.domain.usecase.exception

import com.taetae98.diary.domain.model.exception.ExceptionRelation
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.usecase.SuspendUseCase
import javax.inject.Inject

class DeleteAllExceptionUseCase @Inject constructor(
    private val exceptionRepository: ExceptionRepository
) : SuspendUseCase<List<ExceptionRelation>>(exceptionRepository) {
    override suspend fun execute() = exceptionRepository.findAll().map { entity ->
        ExceptionRelation(
            entity = entity
        )
    }.also {
        exceptionRepository.deleteAll()
    }
}