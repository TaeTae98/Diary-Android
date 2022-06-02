package com.taetae98.diary.feature.place.model

import com.taetae98.diary.domain.model.PlaceEntity

data class PlaceSearchUiState(
    val entity: PlaceEntity,
    val onClick: () -> Unit
) {
    companion object {
        fun from(entity: PlaceEntity, onClick: () -> Unit) = PlaceSearchUiState(
            entity = entity,
            onClick = onClick
        )
    }
}