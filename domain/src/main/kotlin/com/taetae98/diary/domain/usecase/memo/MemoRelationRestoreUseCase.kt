package com.taetae98.diary.domain.usecase.memo

import android.util.Log
import com.taetae98.diary.domain.model.MemoRelation
import com.taetae98.diary.domain.repository.MemoRepository
import com.taetae98.diary.feature.common.getDefaultName
import javax.inject.Inject

class MemoRelationRestoreUseCase @Inject constructor(
    private val memoRepository: MemoRepository
) {
    suspend operator fun invoke(relation: MemoRelation) = runCatching {
        memoRepository.insert(relation.memoEntity)
    }.onFailure {
        Log.e("Memo", MemoRelationRestoreUseCase::class.getDefaultName(), it)
    }
}