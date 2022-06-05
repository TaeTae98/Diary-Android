package com.taetae98.diary.feature.memo.event

sealed class MemoDetailEvent {
    object Edit : MemoDetailEvent()
    object NoTitle : MemoDetailEvent()
    data class Error(val throwable: Throwable) : MemoDetailEvent()
}