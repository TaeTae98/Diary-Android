package com.taetae98.diary.domain.model.table

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.taetae98.diary.domain.model.memo.MemoEntity
import com.taetae98.diary.domain.model.place.PlaceEntity

@Entity(
    primaryKeys = [
        "memoId", "placeId"
    ],
    indices = [
        Index(value = ["updatedAt"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = MemoEntity::class,
            parentColumns = ["id"],
            childColumns = ["memoId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PlaceEntity::class,
            parentColumns = ["id"],
            childColumns = ["placeId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
    ]
)
data class MemoPlaceEntity(
    val memoId: Long,
    val placeId: Long,
    val updatedAt: Long = System.currentTimeMillis()
)