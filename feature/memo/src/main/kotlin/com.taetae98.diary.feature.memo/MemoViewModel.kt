package com.taetae98.diary.feature.memo

import androidx.lifecycle.ViewModel
import com.taetae98.diary.domain.MemoPagingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MemoViewModel @Inject constructor(
    private val memoPagingUseCase: MemoPagingUseCase
) : ViewModel() {
    fun getMemoByTagIds(ids: Collection<Int>) = memoPagingUseCase(ids)
}