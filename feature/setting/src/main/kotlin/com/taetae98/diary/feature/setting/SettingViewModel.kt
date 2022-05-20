package com.taetae98.diary.feature.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taetae98.diary.domain.usecase.setting.GetIsRunOnUnlockHideNotificationUseCase
import com.taetae98.diary.domain.usecase.setting.GetIsRunOnUnlockUseCase
import com.taetae98.diary.domain.usecase.setting.SetIsRunOnUnlockHideNotificationUseCase
import com.taetae98.diary.domain.usecase.setting.SetIsRunOnUnlockUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class SettingViewModel @Inject constructor(
    getIsRunOnUnlockUseCase: GetIsRunOnUnlockUseCase,
    getIsRunOnUnlockHideNotificationUseCase: GetIsRunOnUnlockHideNotificationUseCase,
    private val setIsRunOnUnlockUseCase: SetIsRunOnUnlockUseCase,
    private val setIsRunOnUnlockHideNotificationUseCase: SetIsRunOnUnlockHideNotificationUseCase,
) : ViewModel() {
    val event = MutableSharedFlow<SettingEvent>()

    val isRunOnUnlock = getIsRunOnUnlockUseCase()
        .getOrElse {
            viewModelScope.launch { event.emit(SettingEvent.Error(it)) }
            emptyFlow()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            false
        )

    val isRunOnUnlockOptimized = getIsRunOnUnlockHideNotificationUseCase()
        .getOrElse {
            viewModelScope.launch { event.emit(SettingEvent.Error(it)) }
            emptyFlow()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            false
        )

    fun setIsRunOnUnlock(value: Boolean) {
        viewModelScope.launch {
            setIsRunOnUnlockUseCase(value).onFailure {
                event.emit(SettingEvent.Error(it))
            }
        }
    }

    fun setIsRunOnUnlockOptimized(value: Boolean) {
        viewModelScope.launch {
            setIsRunOnUnlockHideNotificationUseCase(value).onFailure {
                event.emit(SettingEvent.Error(it))
            }
        }
    }
}