package com.taetae98.diary.domain.usecase.folder

import androidx.paging.PagingData
import com.taetae98.diary.domain.model.file.FolderEntity
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.FolderRepository
import com.taetae98.diary.domain.usecase.ParamUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class PagingFolderByParentIdUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val folderRepository: FolderRepository
) : ParamUseCase<PagingFolderByParentIdUseCase.Id, Flow<PagingData<FolderEntity>>>(
    exceptionRepository
) {
    @JvmInline
    value class Id(val id: Long?)

    override fun execute(parameter: Id) = folderRepository.pagingByParentId(parameter.id)
}