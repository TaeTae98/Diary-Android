package com.taetae98.diary.domain.usecase.setting

import com.taetae98.diary.domain.service.RunOnUnlockService
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.SettingRepository
import com.taetae98.diary.domain.usecase.SuspendParamUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class SetRunOnUnlockNotificationVisibleUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val settingRepository: SettingRepository,
    private val runOnUnlockService: RunOnUnlockService,
) : SuspendParamUseCase<SetRunOnUnlockNotificationVisibleUseCase.IsVisible, Unit>(
    exceptionRepository
) {
    @JvmInline
    value class IsVisible(val isVisible: Boolean)

    override suspend fun execute(parameter: IsVisible) {
        settingRepository.setRunOnUnlockNotificationVisible(parameter.isVisible)
        if (settingRepository.isRunOnUnlockEnable().first()) {
            runOnUnlockService.startService()
        } else {
            runOnUnlockService.stopService()
        }
    }
}