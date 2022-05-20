package com.taetae98.diary.domain.usecase.setting

import android.util.Log
import com.taetae98.diary.domain.repository.SettingRepository
import com.taetae98.diary.feature.common.getDefaultName
import javax.inject.Inject

class GetIsRunOnUnlockUseCase @Inject constructor(
    private val settingRepository: SettingRepository
) {
    operator fun invoke() = runCatching {
        settingRepository.isRunOnUnlock()
    }.onFailure {
        Log.e("Setting", GetIsRunOnUnlockUseCase::class.getDefaultName(), it)
    }
}