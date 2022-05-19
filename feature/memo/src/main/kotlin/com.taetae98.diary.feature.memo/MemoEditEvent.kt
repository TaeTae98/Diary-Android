package com.taetae98.diary.feature.memo

sealed class MemoEditEvent {
    object Success : MemoEditEvent()
    data class Error(val throwable: Throwable) : MemoEditEvent()
    object TitleEmpty : MemoEditEvent()
}