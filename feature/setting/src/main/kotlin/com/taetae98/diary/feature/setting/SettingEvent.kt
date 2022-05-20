package com.taetae98.diary.feature.setting

sealed class SettingEvent {
    data class Error(val throwable: Throwable) : SettingEvent()
}