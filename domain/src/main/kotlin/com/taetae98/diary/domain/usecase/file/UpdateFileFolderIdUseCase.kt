package com.taetae98.diary.domain.usecase.file

import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.FileRepository
import com.taetae98.diary.domain.repository.FolderRepository
import com.taetae98.diary.domain.usecase.SuspendParamUseCase
import javax.inject.Inject

class UpdateFileFolderIdUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val fileRepository: FileRepository,
    private val folderRepository: FolderRepository
) : SuspendParamUseCase<UpdateFileFolderIdUseCase.Parameter, Unit>(exceptionRepository) {
    data class Parameter(
        val targetId: Long?,
        val fileId: Collection<Long>,
        val folderId: Collection<Long>
    )

    override suspend fun execute(parameter: Parameter) {
        fileRepository.updateFolderIdByIds(
            folderId = parameter.targetId,
            ids = parameter.fileId
        )

        folderRepository.updateParentIdByIds(
            parentId = parameter.targetId,
            ids = parameter.folderId
        )
    }
}