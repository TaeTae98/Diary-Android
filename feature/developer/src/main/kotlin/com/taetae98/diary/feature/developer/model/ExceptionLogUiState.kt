package com.taetae98.diary.feature.developer.model

import com.taetae98.diary.domain.model.exception.ExceptionEntity
import java.text.SimpleDateFormat

data class ExceptionLogUiState(
    val entity: ExceptionEntity,
    val onDelete: () -> Unit
) {
    val cause = "Cause : ${entity.cause}"
    val createdAt = SimpleDateFormat.getInstance().format(entity.createdAt)
}