package com.taetae98.diary.feature.memo.model

import com.taetae98.diary.domain.model.place.PlaceEntity

data class PlaceUiState(
    val entity: PlaceEntity,
    val onClick: () -> Unit,
    val onDelete: () -> Unit
)