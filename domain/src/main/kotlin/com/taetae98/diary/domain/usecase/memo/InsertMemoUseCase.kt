package com.taetae98.diary.domain.usecase.memo

import android.util.Log
import com.taetae98.diary.domain.model.MemoEntity
import com.taetae98.diary.domain.repository.MemoRepository
import com.taetae98.diary.feature.common.getDefaultName
import javax.inject.Inject

class InsertMemoUseCase @Inject constructor(
    private val memoRepository: MemoRepository
) {
    suspend operator fun invoke(entity: MemoEntity) = runCatching {
        memoRepository.insert(entity)
    }.onFailure {
        Log.e("Memo", InsertMemoUseCase::class.getDefaultName(), it)
    }
}