package com.taetae98.diary.feature.file.event

sealed class FileDetailEvent {
    data class Error(val throwable: Throwable) : FileDetailEvent()
    object NoTitle : FileDetailEvent()
    object Insert : FileDetailEvent()
    object Update : FileDetailEvent()
}