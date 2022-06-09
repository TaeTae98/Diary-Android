package com.taetae98.diary.domain.usecase.folder

import com.taetae98.diary.domain.model.file.FolderEntity
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.FolderRepository
import com.taetae98.diary.domain.usecase.SuspendParamUseCase
import javax.inject.Inject

class InsertFolderUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val folderRepository: FolderRepository
) : SuspendParamUseCase<FolderEntity, Long>(exceptionRepository) {
    override suspend fun execute(parameter: FolderEntity) = folderRepository.insert(parameter)
}