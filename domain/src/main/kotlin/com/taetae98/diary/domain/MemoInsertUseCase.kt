package com.taetae98.diary.domain

import android.util.Log
import javax.inject.Inject

class MemoInsertUseCase @Inject constructor(
    private val memoRepository: MemoRepository
) {
    suspend operator fun invoke(entity: MemoEntity) = runCatching {
        memoRepository.insert(entity)
    }.onFailure {
        Log.e("Memo", "MemoInsertUseCase", it)
    }
}