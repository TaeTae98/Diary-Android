package com.taetae98.diary.feature.compose.file

import com.taetae98.diary.domain.model.file.FileEntity

data class FilePreviewUiState(
    val entity: FileEntity,
    val onClick: () -> Unit,
    val onLongClick: () -> Unit
)