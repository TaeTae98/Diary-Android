package com.taetae98.diary.feature.memo.event

import com.taetae98.diary.domain.model.memo.MemoRelation

sealed class MemoEvent {
    data class SecurityAction(
        val password: String,
        val onAction: () -> Unit
    ) : MemoEvent()
    data class Detail(
        val id: Long
    ) : MemoEvent()

    data class Delete(
        val relation: MemoRelation,
        val onRestore: () -> Unit
    ) : MemoEvent()

    data class Error(val throwable: Throwable) : MemoEvent()
}