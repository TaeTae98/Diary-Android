package com.taetae98.diary.feature.location.event

import com.taetae98.diary.domain.model.LocationSearchQueryRelation

sealed class LocationSearchEvent {
    data class Error(val throwable: Throwable) : LocationSearchEvent()
    data class Search(val query: String) : LocationSearchEvent()
    data class Delete(val relation: LocationSearchQueryRelation) : LocationSearchEvent()
}