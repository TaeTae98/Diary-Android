package com.taetae98.diary.domain.usecase

import com.taetae98.diary.domain.repository.ExceptionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class SuspendUseCase<R>(
    private val exceptionRepository: ExceptionRepository
) {
    suspend operator fun invoke() = runCatching {
        execute()
    }.onFailure {
        CoroutineScope(Dispatchers.IO).launch {
            exceptionRepository.insert(it)
        }
    }

    abstract suspend fun execute(): R
}