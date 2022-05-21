package com.taetae98.diary.domain.usecase.developer

import com.taetae98.diary.domain.repository.DeveloperRepository
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.usecase.UseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetDeveloperModePasswordUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val developerRepository: DeveloperRepository
) : UseCase<Flow<String?>>(exceptionRepository) {
    override fun execute() = developerRepository.getDeveloperModePassword()
}