package com.taetae98.diary.domain.usecase.file.developer

import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.FileRepository
import com.taetae98.diary.domain.usecase.SuspendParamUseCase
import com.taetae98.diary.feature.common.util.isFalse
import java.io.File
import javax.inject.Inject

class IsWarningFileUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val fileRepository: FileRepository
) : SuspendParamUseCase<File, Boolean>(exceptionRepository) {
    override suspend fun execute(parameter: File) = fileRepository.containByPath(parameter.path).isFalse()
}