package com.taetae98.diary.feature.file.event

sealed class FileEvent {
    data class Error(val throwable: Throwable) : FileEvent()
    data class SecurityAction(
        val password: String,
        val onAction: () -> Unit
    ) : FileEvent()
}