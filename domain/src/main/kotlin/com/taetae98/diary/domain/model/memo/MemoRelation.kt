package com.taetae98.diary.domain.model.memo

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.taetae98.diary.domain.model.file.FileEntity
import com.taetae98.diary.domain.model.place.PlaceEntity
import com.taetae98.diary.domain.model.table.MemoFileEntity
import com.taetae98.diary.domain.model.table.MemoPlaceEntity

data class MemoRelation(
    @Embedded
    val memo: MemoEntity = MemoEntity(),

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = MemoPlaceEntity::class,
            parentColumn = "memoId",
            entityColumn = "placeId"
        )
    )
    val place: List<PlaceEntity> = emptyList(),

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = MemoFileEntity::class,
            parentColumn = "memoId",
            entityColumn = "fileId"
        )
    )
    val file: List<FileEntity> = emptyList()
)