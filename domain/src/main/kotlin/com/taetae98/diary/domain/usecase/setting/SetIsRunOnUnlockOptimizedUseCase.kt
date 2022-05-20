package com.taetae98.diary.domain.usecase.setting

import android.util.Log
import com.taetae98.diary.domain.call.RunOnUnlockCall
import com.taetae98.diary.domain.repository.SettingRepository
import com.taetae98.diary.feature.common.getDefaultName
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class SetIsRunOnUnlockOptimizedUseCase @Inject constructor(
    private val settingRepository: SettingRepository,
    private val runOnUnlockCall: RunOnUnlockCall
) {
    suspend operator fun invoke(value: Boolean) = runCatching {
        if (settingRepository.isRunOnUnlock().first()) {
            if (value) {
                runOnUnlockCall.startBackgroundService()
            } else {
                runOnUnlockCall.startForegroundService()
            }
        } else {
            runOnUnlockCall.stopService()
        }

        settingRepository.setIsRunOnUnlockOptimized(value)
    }.onFailure {
        Log.e("Setting", SetIsRunOnUnlockOptimizedUseCase::class.getDefaultName(), it)
    }
}