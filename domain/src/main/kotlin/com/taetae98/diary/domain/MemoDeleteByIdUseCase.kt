package com.taetae98.diary.domain

import android.util.Log
import javax.inject.Inject

class MemoDeleteByIdUseCase @Inject constructor(
    private val memoRepository: MemoRepository
) {
    suspend operator fun invoke(id: Int) = runCatching {
        MemoRelation(
            memoEntity = memoRepository.findById(id)
        ).also {
            memoRepository.deleteById(id)
        }
    }.onFailure {
        Log.e("Memo", "MemoDeleteByIdUseCase", it)
    }
}