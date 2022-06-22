package com.taetae98.diary.feature.memo.model

import com.taetae98.diary.domain.model.memo.MemoEntity

data class MemoPreviewUiState(
    val entity: MemoEntity,
    val onClick: () -> Unit,
    val onDelete: () -> Unit
)