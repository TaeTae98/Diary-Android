package com.taetae98.diary.feature.place.event

sealed class PlaceDetailEvent {
    object Edit : PlaceDetailEvent()
    object NoPlace : PlaceDetailEvent()
    data class Error(val throwable: Throwable) : PlaceDetailEvent()
}