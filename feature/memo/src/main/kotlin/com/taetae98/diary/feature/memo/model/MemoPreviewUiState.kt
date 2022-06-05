package com.taetae98.diary.feature.memo.model

import com.taetae98.diary.domain.model.memo.MemoEntity

data class MemoPreviewUiState(
    val id: Long,
    val title: String,
    val onClick: () -> Unit,
    val onDelete: () -> Unit
) {
    companion object {
        fun from(entity: MemoEntity, onClick: () -> Unit, onDelete: () -> Unit) = MemoPreviewUiState(
            id = entity.id,
            title = entity.title,
            onClick = onClick,
            onDelete = onDelete
        )
    }
}