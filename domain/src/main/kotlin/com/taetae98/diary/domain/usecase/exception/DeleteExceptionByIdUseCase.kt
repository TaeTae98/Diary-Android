package com.taetae98.diary.domain.usecase.exception

import com.taetae98.diary.domain.model.exception.ExceptionRelation
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.usecase.SuspendParamUseCase
import javax.inject.Inject

class DeleteExceptionByIdUseCase @Inject constructor(
    private val exceptionRepository: ExceptionRepository
) : SuspendParamUseCase<DeleteExceptionByIdUseCase.Id, ExceptionRelation>(exceptionRepository) {
    @JvmInline
    value class Id(val id: Long)

    override suspend fun execute(parameter: Id) = ExceptionRelation(
        entity = exceptionRepository.findById(parameter.id)
    ).also {
        exceptionRepository.deleteById(parameter.id)
    }
}