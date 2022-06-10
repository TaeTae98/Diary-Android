package com.taetae98.diary.domain.usecase.file

import com.taetae98.diary.domain.model.file.FileEntity
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.FileRepository
import com.taetae98.diary.domain.usecase.ParamUseCase
import javax.inject.Inject

class ExplorerFileUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val fileRepository: FileRepository
) : ParamUseCase<FileEntity, Unit>(exceptionRepository){
    override fun execute(parameter: FileEntity) = fileRepository.explore(parameter)
}