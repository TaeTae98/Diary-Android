package com.taetae98.diary.domain.usecase.exception

import com.taetae98.diary.domain.model.exception.ExceptionRelation
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.usecase.SuspendParamUseCase
import javax.inject.Inject

class RestoreExceptionRelationUseCase @Inject constructor(
    private val exceptionRepository: ExceptionRepository
) : SuspendParamUseCase<Collection<ExceptionRelation>, Unit>(exceptionRepository) {
    override suspend fun execute(parameter: Collection<ExceptionRelation>) {
        exceptionRepository.insert(
            parameter.map { it.entity }
        )
    }
}