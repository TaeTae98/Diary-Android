package com.taetae98.diary.domain.usecase.memo

import android.util.Log
import com.taetae98.diary.domain.repository.MemoRepository
import com.taetae98.diary.feature.common.getDefaultName
import javax.inject.Inject

class PagingMemoByTagIdsUseCase @Inject constructor(
    private val memoRepository: MemoRepository
) {
    operator fun invoke(ids: Collection<Int>) = runCatching {
        memoRepository.findByTagIds(ids)
    }.onFailure {
        Log.e("Memo", PagingMemoByTagIdsUseCase::class.getDefaultName(), it)
    }
}