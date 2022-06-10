package com.taetae98.diary.domain.usecase.file

import android.net.Uri
import com.taetae98.diary.domain.model.file.FileEntity
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.FileRepository
import com.taetae98.diary.domain.usecase.SuspendParamUseCase
import javax.inject.Inject

class InsertFileUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val fileRepository: FileRepository
) : SuspendParamUseCase<InsertFileUseCase.Parameter, Long>(exceptionRepository) {
    data class Parameter(
        val entity: FileEntity,
        val uri: Uri
    )

    override suspend fun execute(parameter: Parameter) = fileRepository.insert(
        entity = parameter.entity,
        uri = parameter.uri
    )
}