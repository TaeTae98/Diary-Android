package com.taetae98.diary.feature.memo

sealed class MemoEditEvent {
    object Success : MemoEditEvent()
    object TitleEmpty : MemoEditEvent()
}