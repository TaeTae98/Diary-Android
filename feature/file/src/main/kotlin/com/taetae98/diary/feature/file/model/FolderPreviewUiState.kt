package com.taetae98.diary.feature.file.model

import com.taetae98.diary.domain.model.file.FolderEntity

data class FolderPreviewUiState(
    val entity: FolderEntity = FolderEntity(),
    val onClick: () -> Unit = { }
) {
    companion object {
        fun from(entity: FolderEntity, onClick: () -> Unit) = FolderPreviewUiState(
            entity = entity,
            onClick = onClick
        )
    }
}