package com.taetae98.diary.feature.file.event

import com.taetae98.diary.domain.model.file.FileEntity

sealed class FileEvent {
    data class Error(val throwable: Throwable) : FileEvent()
    data class SecurityAction(
        val password: String,
        val onAction: () -> Unit
    ) : FileEvent()
    data class OnClickFile(val entity: FileEntity) : FileEvent()
}