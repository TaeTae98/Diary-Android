package com.taetae98.diary.domain

import javax.inject.Inject

class MemoInsertUseCase @Inject constructor(
    private val memoRepository: MemoRepository
) {
    suspend operator fun invoke(entity: MemoEntity) = memoRepository.insert(entity)
}