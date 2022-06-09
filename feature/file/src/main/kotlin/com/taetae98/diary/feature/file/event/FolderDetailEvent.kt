package com.taetae98.diary.feature.file.event

sealed class FolderDetailEvent {
    data class Error(val throwable: Throwable) : FolderDetailEvent()
    object NoTitle : FolderDetailEvent()
    object Edit : FolderDetailEvent()
}