package com.taetae98.diary.domain.usecase.file

import com.taetae98.diary.domain.model.file.FileEntity
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.FileRepository
import com.taetae98.diary.domain.usecase.SuspendParamUseCase
import javax.inject.Inject

class FindFileByIdUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val fileRepository: FileRepository
) : SuspendParamUseCase<FindFileByIdUseCase.Id, FileEntity>(exceptionRepository) {
    @JvmInline
    value class Id(val id: Long)

    override suspend fun execute(parameter: Id) = fileRepository.findById(parameter.id)
}