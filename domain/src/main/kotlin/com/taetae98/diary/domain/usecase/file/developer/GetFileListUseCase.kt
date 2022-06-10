package com.taetae98.diary.domain.usecase.file.developer

import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.FileRepository
import com.taetae98.diary.domain.usecase.UseCase
import java.io.File
import javax.inject.Inject

class GetFileListUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val fileRepository: FileRepository
) : UseCase<List<File>>(exceptionRepository) {
    override fun execute() = fileRepository.getFileList()
}