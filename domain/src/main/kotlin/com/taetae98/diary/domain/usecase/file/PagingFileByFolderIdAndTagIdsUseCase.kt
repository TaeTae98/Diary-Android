package com.taetae98.diary.domain.usecase.file

import androidx.paging.PagingData
import com.taetae98.diary.domain.model.file.FileEntity
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.FileRepository
import com.taetae98.diary.domain.usecase.ParamUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class PagingFileByFolderIdAndTagIdsUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val fileRepository: FileRepository
) : ParamUseCase<PagingFileByFolderIdAndTagIdsUseCase.Parameter, Flow<PagingData<FileEntity>>>(
    exceptionRepository
) {
    data class Parameter(
        val folderId: Long?,
        val tagIds: Collection<Long>
    )

    override fun execute(parameter: Parameter) = fileRepository.pagingByFolderIdAndTagIds(
        folderId = parameter.folderId,
        tagIds = parameter.tagIds
    )
}