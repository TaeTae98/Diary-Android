package com.taetae98.diary.feature.file.model

import com.taetae98.diary.domain.model.file.FileEntity

data class FilePreviewUiState(
    val entity: FileEntity,
    val onClick: () -> Unit
) {
    companion object {
        fun from(entity: FileEntity, onClick: () -> Unit) = FilePreviewUiState(
            entity = entity,
            onClick = onClick
        )
    }
}