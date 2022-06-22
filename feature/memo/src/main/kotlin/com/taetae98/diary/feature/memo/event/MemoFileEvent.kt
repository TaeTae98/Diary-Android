package com.taetae98.diary.feature.memo.event

sealed class MemoFileEvent {
    data class Error(val throwable: Throwable) : MemoFileEvent()
}