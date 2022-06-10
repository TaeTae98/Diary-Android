package com.taetae98.diary.domain.usecase.file

import com.taetae98.diary.domain.model.file.FileEntity
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.FileRepository
import com.taetae98.diary.domain.usecase.SuspendParamUseCase
import javax.inject.Inject

class UpdateFileUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val fileRepository: FileRepository
) : SuspendParamUseCase<FileEntity, Int>(exceptionRepository) {
    override suspend fun execute(parameter: FileEntity) = fileRepository.update(parameter)
}