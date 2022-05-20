package com.taetae98.diary.domain.usecase.setting

import android.util.Log
import com.taetae98.diary.domain.call.RunOnUnlockCall
import com.taetae98.diary.domain.repository.SettingRepository
import com.taetae98.diary.feature.common.getDefaultName
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class SetIsRunOnUnlockHideNotificationUseCase @Inject constructor(
    private val settingRepository: SettingRepository,
    private val runOnUnlockCall: RunOnUnlockCall
) {
    suspend operator fun invoke(value: Boolean) = runCatching {
        settingRepository.setIsRunOnUnlockHideNotification(value)
        if (settingRepository.isRunOnUnlock().first()) {
            runOnUnlockCall.startService()
        } else {
            runOnUnlockCall.stopService()
        }
    }.onFailure {
        Log.e("Setting", SetIsRunOnUnlockHideNotificationUseCase::class.getDefaultName(), it)
    }
}