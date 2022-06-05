package com.taetae98.diary.feature.place.model

import com.taetae98.diary.domain.model.PlaceEntity

data class PlaceUiState(
    val entity: PlaceEntity,
    val onClick: () -> Unit,
    val onDelete: () -> Unit,
) {
    companion object {
        fun from(entity: PlaceEntity, onClick: () -> Unit, onDelete: () -> Unit) = PlaceUiState(
            entity = entity,
            onClick = onClick,
            onDelete = onDelete
        )
    }
}