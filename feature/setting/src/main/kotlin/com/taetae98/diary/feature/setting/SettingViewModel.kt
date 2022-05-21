package com.taetae98.diary.feature.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taetae98.diary.domain.usecase.setting.IsRunOnUnlockEnableUseCase
import com.taetae98.diary.domain.usecase.setting.IsRunOnUnlockNotificationVisibleUseCase
import com.taetae98.diary.domain.usecase.setting.SetRunOnUnlockEnableUseCase
import com.taetae98.diary.domain.usecase.setting.SetRunOnUnlockNotificationVisibleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class SettingViewModel @Inject constructor(
    isRunOnUnlockEnableUseCase: IsRunOnUnlockEnableUseCase,
    isRunOnUnlockNotificationVisibleUseCase: IsRunOnUnlockNotificationVisibleUseCase,
    private val setRunOnUnlockEnableUseCase: SetRunOnUnlockEnableUseCase,
    private val setRunOnUnlockNotificationVisibleUseCase: SetRunOnUnlockNotificationVisibleUseCase,
) : ViewModel() {
    val event = MutableSharedFlow<SettingEvent>()

    val isRunOnUnlockAvailable = isRunOnUnlockEnableUseCase()
        .getOrElse {
            viewModelScope.launch { event.emit(SettingEvent.Error(it)) }
            emptyFlow()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = false
        )

    val isRunOnUnlockNotificationVisible = isRunOnUnlockNotificationVisibleUseCase()
        .getOrElse {
            viewModelScope.launch { event.emit(SettingEvent.Error(it)) }
            emptyFlow()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = false
        )

    fun setRunOnUnlockAvailable(isAvailable: Boolean) {
        viewModelScope.launch {
            setRunOnUnlockEnableUseCase(SetRunOnUnlockEnableUseCase.IsEnable(isAvailable)).onFailure {
                event.emit(SettingEvent.Error(it))
            }
        }
    }

    fun setRunOnUnlockNotificationVisible(isVisible: Boolean) {
        viewModelScope.launch {
            setRunOnUnlockNotificationVisibleUseCase(SetRunOnUnlockNotificationVisibleUseCase.IsVisible(isVisible)).onFailure {
                event.emit(SettingEvent.Error(it))
            }
        }
    }
}