package com.taetae98.diary.domain.usecase.memo

import com.taetae98.diary.domain.model.MemoRelation
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.MemoRepository
import com.taetae98.diary.domain.usecase.SuspendParamUseCase
import javax.inject.Inject

class RestoreMemoRelationUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val memoRepository: MemoRepository
) : SuspendParamUseCase<MemoRelation, Long>(exceptionRepository) {
    override suspend fun execute(parameter: MemoRelation) = memoRepository.insert(parameter.memoEntity)
}