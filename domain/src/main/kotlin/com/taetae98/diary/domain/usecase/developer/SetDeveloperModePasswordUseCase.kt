package com.taetae98.diary.domain.usecase.developer

import com.taetae98.diary.domain.repository.DeveloperRepository
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.usecase.SuspendParamUseCase
import javax.inject.Inject

class SetDeveloperModePasswordUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val developerRepository: DeveloperRepository
) : SuspendParamUseCase<SetDeveloperModePasswordUseCase.Password, Unit>(exceptionRepository) {
    @JvmInline
    value class Password(val password: String?)

    override suspend fun execute(parameter: Password)= developerRepository.setDeveloperModePassword(parameter.password)
}