package com.taetae98.diary.feature.developer.model

import com.taetae98.diary.domain.model.ExceptionEntity
import java.text.SimpleDateFormat

data class ExceptionLogUiState(
    val id: Long,
    val type: String,
    val cause: String,
    val createdAt: String,
    val stackTrace: String,
    val onDelete: () -> Unit
) {
    companion object {
        fun from(entity: ExceptionEntity, onDelete: () -> Unit) = ExceptionLogUiState(
            id = entity.id,
            type = entity.type,
            cause = "Cause : ${entity.cause}",
            createdAt = SimpleDateFormat.getInstance().format(entity.createdAt),
            stackTrace = entity.stackTrace,
            onDelete = onDelete
        )
    }
}