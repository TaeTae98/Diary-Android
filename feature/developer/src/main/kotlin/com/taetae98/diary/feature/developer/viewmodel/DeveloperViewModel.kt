package com.taetae98.diary.feature.developer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taetae98.diary.domain.usecase.developer.GetDeveloperModePasswordUseCase
import com.taetae98.diary.domain.usecase.developer.IsDeveloperModeEnableUseCase
import com.taetae98.diary.domain.usecase.developer.SetDeveloperModePasswordUseCase
import com.taetae98.diary.domain.usecase.developer.SetIsDeveloperModeEnableUseCase
import com.taetae98.diary.feature.developer.event.DeveloperEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class DeveloperViewModel @Inject constructor(
    isDeveloperModeEnableUseCase: IsDeveloperModeEnableUseCase,
    getDeveloperModePasswordUseCase: GetDeveloperModePasswordUseCase,
    private val setDeveloperModeEnableUseCase: SetIsDeveloperModeEnableUseCase,
    private val setDeveloperModePasswordUseCase: SetDeveloperModePasswordUseCase,
) : ViewModel() {
    val event = MutableSharedFlow<DeveloperEvent>()

    val isDeveloperModeEnable = isDeveloperModeEnableUseCase()
        .getOrElse {
            viewModelScope.launch { event.emit(DeveloperEvent.Error(it)) }
            emptyFlow()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = true
        )

    val password = MutableStateFlow("")
    val hasPassword = MutableStateFlow(false)

    init {
        viewModelScope.launch {
            getDeveloperModePasswordUseCase().getOrElse {
                event.emit(DeveloperEvent.Error(it))
                emptyFlow()
            }.collect {
                if (it != null) {
                    password.emit(it)
                    hasPassword.emit(true)
                }
            }
        }
    }

    fun setDeveloperModeEnable(value: Boolean) {
        viewModelScope.launch {
            setDeveloperModeEnableUseCase(SetIsDeveloperModeEnableUseCase.IsEnable(value))
        }
    }

    fun setHasPassword(value: Boolean) {
        viewModelScope.launch {
            hasPassword.emit(value)
            if (value) {
                setDeveloperModePasswordUseCase(SetDeveloperModePasswordUseCase.Password(password.value))
            } else {
                setDeveloperModePasswordUseCase(SetDeveloperModePasswordUseCase.Password(null))
            }
        }
    }

    fun setPassword(value: String) {
        viewModelScope.launch {
            password.emit(value)
            setDeveloperModePasswordUseCase(SetDeveloperModePasswordUseCase.Password(value))
        }
    }
}