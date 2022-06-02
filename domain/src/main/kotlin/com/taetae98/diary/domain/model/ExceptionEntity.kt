package com.taetae98.diary.domain.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.taetae98.diary.feature.common.util.getDefaultName

@Entity(
    indices = [
        Index(
            value = ["type"]
        )
    ]
)
data class ExceptionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val cause: String?,
    val stackTrace: String,
    val type: String,
    val createdAt: Long = System.currentTimeMillis()
) {
    constructor(throwable: Throwable) : this(
        id = 0,
        cause = throwable.cause?.toString(),
        stackTrace = throwable.stackTraceToString(),
        type = throwable::class.getDefaultName()
    )
}