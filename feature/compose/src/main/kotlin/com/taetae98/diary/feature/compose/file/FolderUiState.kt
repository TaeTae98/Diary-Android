package com.taetae98.diary.feature.compose.file

data class FolderUiState(
    val title: String = "",
    val onClick: () -> Unit = {},
)