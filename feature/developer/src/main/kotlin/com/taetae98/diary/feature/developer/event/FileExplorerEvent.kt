package com.taetae98.diary.feature.developer.event

sealed class FileExplorerEvent {
    data class Error(val throwable: Throwable) : FileExplorerEvent()
}