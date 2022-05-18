package com.taetae98.diary.feature.memo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taetae98.diary.domain.MemoEntity
import com.taetae98.diary.domain.MemoInsertUseCase
import com.taetae98.diary.feature.common.Parameter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MemoEditViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val memoInsertUseCase: MemoInsertUseCase,
) : ViewModel() {
    val title = MutableStateFlow("")
    val description = MutableStateFlow("")
    val password = MutableStateFlow("")
    val hasPassword = MutableStateFlow(false)

    val event = MutableSharedFlow<MemoEditEvent>()

    fun setTitle(value: String) {
        viewModelScope.launch { title.emit(value) }
    }

    fun setDescription(value: String) {
        viewModelScope.launch { description.emit(value) }
    }

    fun setPassword(value: String) {
        viewModelScope.launch { password.emit(value) }
    }

    fun toggleHasPassword() {
        viewModelScope.launch { hasPassword.emit(hasPassword.value.not()) }
    }

    fun edit() {
        viewModelScope.launch {
            if (title.value.isEmpty()) {
                event.emit(MemoEditEvent.TitleEmpty)
            } else {
                memoInsertUseCase(
                    MemoEntity(
                        title = title.value,
                        description = description.value,
                        password = if (hasPassword.value) password.value else null
                    )
                )
                event.emit(MemoEditEvent.Success)
            }
        }
    }

    fun clear() {
        viewModelScope.launch {
            title.emit("")
            description.emit("")
            password.emit("")
            hasPassword.emit(false)
        }
    }

    fun isEditMode(): Boolean {
        return (savedStateHandle.get<Int>(Parameter.MEMO_ID) ?: 0) != 0
    }
}