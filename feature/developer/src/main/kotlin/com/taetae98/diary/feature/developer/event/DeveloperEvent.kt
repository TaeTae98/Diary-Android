package com.taetae98.diary.feature.developer.event

sealed class DeveloperEvent {
    data class Error(val throwable: Throwable) : DeveloperEvent()
}