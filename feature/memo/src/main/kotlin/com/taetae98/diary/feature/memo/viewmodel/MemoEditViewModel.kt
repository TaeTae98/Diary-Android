package com.taetae98.diary.feature.memo.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taetae98.diary.domain.model.MemoEntity
import com.taetae98.diary.domain.usecase.memo.InsertMemoUseCase
import com.taetae98.diary.feature.common.Parameter
import com.taetae98.diary.feature.memo.event.MemoEditEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MemoEditViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val insertMemoUseCase: InsertMemoUseCase,
) : ViewModel() {
    val event = MutableSharedFlow<MemoEditEvent>()

    val title = MutableStateFlow("")
    val description = MutableStateFlow("")
    val password = MutableStateFlow("")
    val hasPassword = MutableStateFlow(false)

    fun setTitle(value: String) {
        viewModelScope.launch { title.emit(value) }
    }

    fun setDescription(value: String) {
        viewModelScope.launch { description.emit(value) }
    }

    fun setPassword(value: String) {
        viewModelScope.launch { password.emit(value) }
    }

    fun setHasPassword(value: Boolean) {
        viewModelScope.launch { hasPassword.emit(value) }
    }

    fun edit() {
        viewModelScope.launch(Dispatchers.IO) {
            if (title.value.isEmpty()) {
                event.emit(MemoEditEvent.TitleEmpty)
            } else {
                insertMemoUseCase(
                    MemoEntity(
                        title = title.value,
                        description = description.value,
                        password = if (hasPassword.value) password.value else null
                    )
                ).onSuccess {
                    event.emit(MemoEditEvent.Success)
                }.onFailure {
                    event.emit(MemoEditEvent.Error(it))
                }
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