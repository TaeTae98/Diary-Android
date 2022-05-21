package com.taetae98.diary.feature.more

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taetae98.diary.domain.usecase.developer.GetDeveloperModePasswordUseCase
import com.taetae98.diary.domain.usecase.developer.IsDeveloperModeEnableUseCase
import com.taetae98.diary.domain.usecase.developer.SetIsDeveloperModeEnableUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class MoreViewModel @Inject constructor(
    isDeveloperModeEnableUseCase: IsDeveloperModeEnableUseCase,
    getDeveloperModePasswordUseCase: GetDeveloperModePasswordUseCase,
    private val setIsDeveloperModeEnableUseCase: SetIsDeveloperModeEnableUseCase
) : ViewModel() {
    val event = MutableSharedFlow<MoreEvent>()

    val isDeveloperModeEnable = isDeveloperModeEnableUseCase().getOrElse {
        viewModelScope.launch { event.emit(MoreEvent.Error(it)) }
        emptyFlow()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = false
    )

    val developerModePassword = getDeveloperModePasswordUseCase().getOrElse {
        viewModelScope.launch { event.emit(MoreEvent.Error(it)) }
        emptyFlow()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = null
    )

    fun setIsDeveloperModeEnable(value: Boolean) {
        viewModelScope.launch {
            setIsDeveloperModeEnableUseCase(SetIsDeveloperModeEnableUseCase.IsEnable(value))
        }
    }
}