package com.taetae98.diary.feature.developer.event

import com.taetae98.diary.domain.model.exception.ExceptionRelation

sealed class ExceptionLogEvent {
    data class DeleteExceptionLog(
        val collection: Collection<ExceptionRelation>,
        val onRestore: () -> Unit
    ) : ExceptionLogEvent()

    data class Error(val throwable: Throwable) : ExceptionLogEvent()
}