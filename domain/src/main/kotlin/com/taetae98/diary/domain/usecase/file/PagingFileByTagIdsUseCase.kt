package com.taetae98.diary.domain.usecase.file

import androidx.paging.PagingData
import com.taetae98.diary.domain.model.file.FileEntity
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.FileRepository
import com.taetae98.diary.domain.usecase.ParamUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class PagingFileByTagIdsUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val fileRepository: FileRepository
) : ParamUseCase<PagingFileByTagIdsUseCase.Ids, Flow<PagingData<FileEntity>>>(exceptionRepository) {
    @JvmInline
    value class Ids(val ids: Collection<Long>)

    override fun execute(parameter: Ids) = fileRepository.pagingByTagIds(parameter.ids)
}