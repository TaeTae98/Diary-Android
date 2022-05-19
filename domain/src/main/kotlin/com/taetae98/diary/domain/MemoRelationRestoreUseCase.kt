package com.taetae98.diary.domain

import javax.inject.Inject

class MemoRelationRestoreUseCase @Inject constructor(
    private val memoRepository: MemoRepository
) {
    suspend operator fun invoke(relation: MemoRelation) = runCatching {
        memoRepository.insert(relation.memoEntity)
    }
}