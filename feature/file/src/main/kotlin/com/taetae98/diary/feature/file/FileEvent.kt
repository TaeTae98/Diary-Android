package com.taetae98.diary.feature.file

sealed class FileEvent {
    object Success : FileEvent()
    object Fail : FileEvent()
}