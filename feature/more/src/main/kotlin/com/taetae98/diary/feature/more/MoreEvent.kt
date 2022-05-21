package com.taetae98.diary.feature.more

sealed class MoreEvent {
    data class Error(val throwable: Throwable) : MoreEvent()
}