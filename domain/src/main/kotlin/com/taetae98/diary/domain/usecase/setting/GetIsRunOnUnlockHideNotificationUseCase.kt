package com.taetae98.diary.domain.usecase.setting

import android.util.Log
import com.taetae98.diary.domain.repository.SettingRepository
import com.taetae98.diary.feature.common.getDefaultName
import javax.inject.Inject

class GetIsRunOnUnlockHideNotificationUseCase @Inject constructor(
    private val settingRepository: SettingRepository
) {
    operator fun invoke() = runCatching {
        settingRepository.isRunOnUnlockHideNotification()
    }.onFailure {
        Log.e("Setting", GetIsRunOnUnlockHideNotificationUseCase::class.getDefaultName(), it)
    }
}