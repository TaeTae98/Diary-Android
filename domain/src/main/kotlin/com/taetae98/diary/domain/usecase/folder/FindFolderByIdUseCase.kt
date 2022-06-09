package com.taetae98.diary.domain.usecase.folder

import com.taetae98.diary.domain.model.file.FolderEntity
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.FolderRepository
import com.taetae98.diary.domain.usecase.SuspendParamUseCase
import javax.inject.Inject

class FindFolderByIdUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val folderRepository: FolderRepository
) : SuspendParamUseCase<FindFolderByIdUseCase.Id, FolderEntity>(exceptionRepository) {
    @JvmInline
    value class Id(val id: Long)

    override suspend fun execute(parameter: Id) = folderRepository.findById(parameter.id)
}