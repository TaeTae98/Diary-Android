package com.taetae98.diary.feature.location.model

import com.taetae98.diary.domain.model.LocationSearchQueryEntity
import java.text.SimpleDateFormat

data class LocationSearchQueryUiState(
    val id: Int,
    val query: String,
    val searchedAt: String,
    val onDelete: () -> Unit
) {
    companion object {
        fun from(entity: LocationSearchQueryEntity, onDelete: () -> Unit): LocationSearchQueryUiState {
            return LocationSearchQueryUiState(
                id = entity.id,
                query = entity.query,
                searchedAt = SimpleDateFormat.getDateInstance().format(entity.searchedAt),
                onDelete = onDelete
            )
        }
    }
}