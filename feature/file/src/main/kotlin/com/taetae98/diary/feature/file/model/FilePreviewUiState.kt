package com.taetae98.diary.feature.file.model

import com.taetae98.diary.domain.model.file.FileEntity

data class FilePreviewUiState(
    val entity: FileEntity,
    val onClick: () -> Unit,
    val onLongClick: () -> Unit
) {
    companion object {
        fun from(entity: FileEntity, onClick: () -> Unit, onLongClick: () -> Unit) = FilePreviewUiState(
            entity = entity,
            onClick = onClick,
            onLongClick = onLongClick
        )
    }
}