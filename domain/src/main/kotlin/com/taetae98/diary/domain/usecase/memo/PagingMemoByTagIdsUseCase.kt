package com.taetae98.diary.domain.usecase.memo

import androidx.paging.PagingData
import com.taetae98.diary.domain.model.MemoEntity
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.MemoRepository
import com.taetae98.diary.domain.usecase.ParamUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class PagingMemoByTagIdsUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val memoRepository: MemoRepository,
) : ParamUseCase<PagingMemoByTagIdsUseCase.Ids, Flow<PagingData<MemoEntity>>>(exceptionRepository) {
    @JvmInline
    value class Ids(val ids: Collection<Long>)

    override fun execute(parameter: Ids) = memoRepository.pagingByTagIds(parameter.ids)
}