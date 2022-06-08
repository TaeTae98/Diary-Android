package com.taetae98.diary.feature.place.event

import com.taetae98.diary.domain.model.place.PlaceEntity
import com.taetae98.diary.domain.model.place.PlaceRelation

sealed class PlaceEvent {
    data class SecurityAction(
        val password: String,
        val onAction: () -> Unit
    ) : PlaceEvent()

    data class Detail(val entity: PlaceEntity) : PlaceEvent()

    data class Delete(
        val relation: PlaceRelation,
        val onRestore: () -> Unit
    ) : PlaceEvent()

    data class Error(val throwable: Throwable) : PlaceEvent()
}