package com.taetae98.diary.domain.usecase.file

import com.taetae98.diary.domain.model.file.FolderRelation
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.FileRepository
import com.taetae98.diary.domain.repository.FolderRepository
import com.taetae98.diary.domain.usecase.SuspendParamUseCase
import javax.inject.Inject

class DeleteFileUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val folderRepository: FolderRepository,
    private val fileRepository: FileRepository,
) : SuspendParamUseCase<DeleteFileUseCase.Parameter, Unit>(exceptionRepository) {
    data class Parameter(
        val folderIds: Collection<Long>,
        val fileIds: Collection<Long>
    )

    override suspend fun execute(parameter: Parameter) {
        parameter.folderIds.forEach {
            delete(folderRepository.findRelationById(it))
        }

        parameter.fileIds.forEach {
            fileRepository.deleteById(it)
        }
    }

    private suspend fun delete(relation: FolderRelation) {
        relation.file.forEach { fileRepository.delete(it) }
        relation.folder.forEach { delete(folderRepository.findRelationById(it.id)) }
        folderRepository.delete(relation.entity)
    }
}