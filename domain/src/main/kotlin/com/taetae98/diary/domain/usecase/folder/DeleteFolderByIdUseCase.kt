package com.taetae98.diary.domain.usecase.folder

import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.FolderRepository
import com.taetae98.diary.domain.usecase.SuspendParamUseCase
import javax.inject.Inject

class DeleteFolderByIdUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val folderRepository: FolderRepository
) : SuspendParamUseCase<DeleteFolderByIdUseCase.Id, Int>(exceptionRepository) {
    @JvmInline
    value class Id(val id: Long)

    override suspend fun execute(parameter: Id) = folderRepository.deleteById(parameter.id)
}