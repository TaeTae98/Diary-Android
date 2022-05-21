package com.taetae98.diary.domain.usecase

import com.taetae98.diary.domain.repository.ExceptionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class ParamUseCase<P, R>(
    private val exceptionRepository: ExceptionRepository
) {
    operator fun invoke(parameter: P) = runCatching {
        execute(parameter)
    }.onFailure {
        CoroutineScope(Dispatchers.IO).launch {
            exceptionRepository.insert(it)
        }
    }

    abstract fun execute(parameter: P): R
}