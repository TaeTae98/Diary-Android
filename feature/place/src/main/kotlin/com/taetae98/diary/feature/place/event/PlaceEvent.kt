package com.taetae98.diary.feature.place.event

import com.taetae98.diary.domain.model.PlaceEntity

sealed class PlaceEvent {
    data class Detail(val entity: PlaceEntity) : PlaceEvent()
    data class Error(val throwable: Throwable) : PlaceEvent()
}