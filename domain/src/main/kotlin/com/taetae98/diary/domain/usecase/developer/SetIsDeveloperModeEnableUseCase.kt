package com.taetae98.diary.domain.usecase.developer

import com.taetae98.diary.domain.repository.DeveloperRepository
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.usecase.SuspendParamUseCase
import javax.inject.Inject

class SetIsDeveloperModeEnableUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val developerRepository: DeveloperRepository,
) : SuspendParamUseCase<SetIsDeveloperModeEnableUseCase.IsEnable, Unit>(exceptionRepository) {
    @JvmInline
    value class IsEnable(val isEnable: Boolean)

    override suspend fun execute(parameter: IsEnable) = developerRepository.setIsDeveloperModeEnable(parameter.isEnable)
}