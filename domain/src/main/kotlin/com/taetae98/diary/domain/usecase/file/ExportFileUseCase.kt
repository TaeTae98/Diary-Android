package com.taetae98.diary.domain.usecase.file

import com.taetae98.diary.domain.model.file.FileEntity
import com.taetae98.diary.domain.model.file.FolderEntity
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.FileRepository
import com.taetae98.diary.domain.repository.FolderRepository
import com.taetae98.diary.domain.usecase.SuspendParamUseCase
import java.io.File
import javax.inject.Inject

class ExportFileUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val folderRepository: FolderRepository,
    private val fileRepository: FileRepository
) : SuspendParamUseCase<ExportFileUseCase.Parameter, Unit>(exceptionRepository) {
    data class Parameter(
        val folder: Collection<Long>,
        val file: Collection<Long>
    )

    override suspend fun execute(parameter: Parameter) {
        folderRepository.findByIds(parameter.folder).forEach {
            export(
                parent = null,
                entity = it
            )
        }
        fileRepository.findByIds(parameter.file).forEach {
            export(
                parent = null,
                entity = it
            )
        }
    }

    private suspend fun export(parent: File?, entity: FileEntity) {
        fileRepository.export(parent = parent, entity = entity)
    }

    private suspend fun export(parent: File?, entity: FolderEntity) {
        val file = File(parent, entity.title)
        folderRepository.export(file = parent, entity = entity)
        folderRepository.findByParentId(entity.id).forEach { export(parent = file, entity = it) }
        fileRepository.findByFolderId(entity.id).forEach { export(parent = file, entity = it) }
    }
}