package com.taetae98.diary.feature.place.event

import com.taetae98.diary.domain.model.PlaceSearchQueryRelation

sealed class PlaceSearchEvent {
    data class Error(val throwable: Throwable) : PlaceSearchEvent()
    data class Search(val query: String) : PlaceSearchEvent()
    data class Delete(val relation: PlaceSearchQueryRelation) : PlaceSearchEvent()
}