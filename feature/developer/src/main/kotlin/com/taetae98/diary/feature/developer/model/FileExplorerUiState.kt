package com.taetae98.diary.feature.developer.model

import java.io.File

data class FileExplorerUiState(
    val file: File,
    val isWarning: Boolean
)