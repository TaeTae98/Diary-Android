package com.taetae98.diary.domain.usecase.memo

import android.util.Log
import com.taetae98.diary.domain.model.MemoRelation
import com.taetae98.diary.domain.repository.MemoRepository
import com.taetae98.diary.feature.common.getDefaultName
import javax.inject.Inject

class DeleteMemoByIdUseCase @Inject constructor(
    private val memoRepository: MemoRepository
) {
    suspend operator fun invoke(id: Int) = runCatching {
        MemoRelation(
            memoEntity = memoRepository.findById(id)
        ).also {
            memoRepository.deleteById(id)
        }
    }.onFailure {
        Log.e("Memo", DeleteMemoByIdUseCase::class.getDefaultName(), it)
    }
}