package com.taetae98.diary.domain.model.memo

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.taetae98.diary.domain.model.place.PlaceEntity

data class MemoRelation(
    @Embedded
    val memo: MemoEntity = MemoEntity(),

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            MemoPlaceEntity::class,
            parentColumn = "memoId",
            entityColumn = "placeId"
        )
    )
    val place: List<PlaceEntity> = emptyList()
)