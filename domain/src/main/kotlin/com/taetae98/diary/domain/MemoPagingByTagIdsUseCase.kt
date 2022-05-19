package com.taetae98.diary.domain

import android.util.Log
import javax.inject.Inject

class MemoPagingByTagIdsUseCase @Inject constructor(
    private val memoRepository: MemoRepository
) {
    operator fun invoke(ids: Collection<Int>) = runCatching {
        memoRepository.findByTagIds(ids)
    }.onFailure {
        Log.e("Memo", "MemoPagingByTagIdsUseCase", it)
    }
}