package com.taetae98.diary.domain.usecase.setting

import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.SettingRepository
import com.taetae98.diary.domain.usecase.UseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class IsRunOnUnlockNotificationVisibleUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val settingRepository: SettingRepository,
) : UseCase<Flow<Boolean>>(exceptionRepository) {
    override fun execute() = settingRepository.isRunOnUnlockNotificationVisible()
}