package com.taetae98.diary.feature.memo.event

import com.taetae98.diary.domain.model.MemoRelation

sealed class MemoEvent {
    data class DeleteMemo(
        val relation: MemoRelation,
        val onRestore: () -> Unit
    ) : MemoEvent()

    data class Error(val throwable: Throwable) : MemoEvent()
}