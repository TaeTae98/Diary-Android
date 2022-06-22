package com.taetae98.diary.domain.model.table

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.taetae98.diary.domain.model.file.FileEntity
import com.taetae98.diary.domain.model.memo.MemoEntity

@Entity(
    primaryKeys = [
        "memoId", "fileId"
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
            entity = FileEntity::class,
            parentColumns = ["id"],
            childColumns = ["fileId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
    ]
)
data class MemoFileEntity(
    val memoId: Long,
    val fileId: Long,
    val updatedAt: Long = System.currentTimeMillis()
)