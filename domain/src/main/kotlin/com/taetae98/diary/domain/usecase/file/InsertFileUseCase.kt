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
) : SuspendParamUseCase<InsertFileUseCase.Params, Long>(exceptionRepository) {
    data class Params(
        val entity: FileEntity,
        val uri: Uri
    )

    override suspend fun execute(parameter: Params) = fileRepository.insert(
        entity = parameter.entity,
        uri = parameter.uri
    )
}