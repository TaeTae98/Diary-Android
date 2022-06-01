package com.taetae98.diary.feature.place.model

import com.taetae98.diary.domain.model.PlaceSearchEntity
import java.text.SimpleDateFormat

data class PlaceSearchUiState(
    val id: Long,
    val query: String,
    val searchedAt: String,
    val onDelete: () -> Unit
) {
    companion object {
        fun from(entity: PlaceSearchEntity, onDelete: () -> Unit) =
            PlaceSearchUiState(
                id = entity.id,
                query = entity.query,
                searchedAt = SimpleDateFormat.getDateInstance().format(entity.searchedAt),
                onDelete = onDelete
            )
    }
}