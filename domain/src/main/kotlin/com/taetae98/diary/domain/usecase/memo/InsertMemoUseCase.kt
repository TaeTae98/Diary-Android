package com.taetae98.diary.domain.usecase.memo

import com.taetae98.diary.domain.model.MemoEntity
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.MemoRepository
import com.taetae98.diary.domain.usecase.SuspendParamUseCase
import javax.inject.Inject

class InsertMemoUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val memoRepository: MemoRepository,
) : SuspendParamUseCase<MemoEntity, Long>(exceptionRepository) {
    override suspend fun execute(parameter: MemoEntity) = memoRepository.insert(parameter)
}