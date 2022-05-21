package com.taetae98.diary.domain.usecase.setting

import com.taetae98.diary.domain.call.RunOnUnlockCall
import com.taetae98.diary.domain.repository.ExceptionRepository
import com.taetae98.diary.domain.repository.SettingRepository
import com.taetae98.diary.domain.usecase.SuspendParamUseCase
import javax.inject.Inject

class SetRunOnUnlockEnableUseCase @Inject constructor(
    exceptionRepository: ExceptionRepository,
    private val settingRepository: SettingRepository,
    private val runOnUnlockCall: RunOnUnlockCall,
) : SuspendParamUseCase<SetRunOnUnlockEnableUseCase.IsEnable, Unit>(exceptionRepository) {
    @JvmInline
    value class IsEnable(val value: Boolean)

    override suspend fun execute(parameter: IsEnable) {
        settingRepository.setRunOnUnlockEnable(parameter.value)
        if (parameter.value) {
            runOnUnlockCall.startService()
        } else {
            runOnUnlockCall.stopService()
        }
    }
}