package com.taetae98.diary.feature.compose.file

data class FileUiState(
    val title: String,
    val path: String,
    val onClick: () -> Unit
) {
}