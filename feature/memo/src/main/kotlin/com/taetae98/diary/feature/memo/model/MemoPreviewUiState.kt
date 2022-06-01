package com.taetae98.diary.feature.memo.model

import com.taetae98.diary.domain.model.MemoEntity

data class MemoPreviewUiState(
    val id: Long,
    val title: String,
    val onDelete: () -> Unit
) {
    companion object {
        fun from(entity: MemoEntity, onDelete: () -> Unit) = MemoPreviewUiState(
            id = entity.id,
            title = entity.title,
            onDelete = onDelete
        )
    }
}