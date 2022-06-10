package com.taetae98.diary.domain.model.file

import androidx.room.Embedded
import androidx.room.Relation

data class FolderRelation(
    @Embedded
    val entity: FolderEntity = FolderEntity(),

    @Relation(
        parentColumn = "id",
        entityColumn = "folderId"
    )
    val file: List<FileEntity> = emptyList(),

    @Relation(
        parentColumn = "id",
        entityColumn = "parentId"
    )
    val folder: List<FolderEntity> = emptyList()
)