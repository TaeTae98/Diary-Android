package com.taetae98.diary.domain.model.place

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.taetae98.diary.domain.model.memo.MemoEntity
import com.taetae98.diary.domain.model.table.MemoPlaceEntity

data class PlaceRelation(
    @Embedded
    val place: PlaceEntity = PlaceEntity(),

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = MemoPlaceEntity::class,
            parentColumn = "placeId",
            entityColumn = "memoId"
        )
    )
    val memo: List<MemoEntity> = emptyList()
)