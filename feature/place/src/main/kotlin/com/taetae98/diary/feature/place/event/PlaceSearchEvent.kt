package com.taetae98.diary.feature.place.event

import android.os.Parcelable
import com.taetae98.diary.domain.model.PlaceEntity
import kotlinx.parcelize.Parcelize

sealed class PlaceSearchEvent {
    @Parcelize
    data class Search(val entity: PlaceEntity) : PlaceSearchEvent(), Parcelable
    data class Error(val throwable: Throwable) : PlaceSearchEvent()
}