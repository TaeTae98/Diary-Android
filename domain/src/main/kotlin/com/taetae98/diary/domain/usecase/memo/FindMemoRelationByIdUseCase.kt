package com.taetae98.diary.domain.usecase.memo

import com.taetae98.diary.domain.model.memo.MemoRelation
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.MemoRepository
import com.taetae98.diary.domain.usecase.SuspendParamUseCase
import javax.inject.Inject

class FindMemoRelationByIdUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val memoRepository: MemoRepository,
) : SuspendParamUseCase<FindMemoRelationByIdUseCase.Id, MemoRelation>(exceptionRepository) {
    @JvmInline
    value class Id(val id: Long)

    override suspend fun execute(parameter: Id) = memoRepository.findRelationById(parameter.id)
}