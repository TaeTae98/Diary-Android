package com.taetae98.diary.domain.usecase.folder

import com.taetae98.diary.domain.model.file.FolderRelation
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.FileRepository
import com.taetae98.diary.domain.repository.FolderRepository
import com.taetae98.diary.domain.usecase.SuspendParamUseCase
import javax.inject.Inject

class DeleteFolderByIdUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val folderRepository: FolderRepository,
    private val fileRepository: FileRepository,
) : SuspendParamUseCase<DeleteFolderByIdUseCase.Id, FolderRelation>(exceptionRepository) {
    @JvmInline
    value class Id(val id: Long)

    override suspend fun execute(parameter: Id) = folderRepository.findRelationById(
        parameter.id
    ).also {
        delete(it)
    }

    private suspend fun delete(relation: FolderRelation) {
        relation.file.forEach { fileRepository.delete(it) }
        relation.folder.forEach { delete(folderRepository.findRelationById(it.id)) }
        folderRepository.delete(relation.entity)
    }
}