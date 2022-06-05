package com.taetae98.diary.feature.place.event

import com.taetae98.diary.domain.model.PlaceEntity

sealed class PlaceDetailEvent {
    data class Edit(val entity: PlaceEntity) : PlaceDetailEvent()
    object NoPlace : PlaceDetailEvent()
    object NoTitle : PlaceDetailEvent()
    data class Error(val throwable: Throwable) : PlaceDetailEvent()
}