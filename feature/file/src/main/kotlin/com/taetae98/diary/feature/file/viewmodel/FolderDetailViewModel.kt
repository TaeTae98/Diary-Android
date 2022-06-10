package com.taetae98.diary.feature.file.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taetae98.diary.domain.model.file.FolderEntity
import com.taetae98.diary.domain.usecase.folder.FindFolderByIdUseCase
import com.taetae98.diary.domain.usecase.folder.InsertFolderUseCase
import com.taetae98.diary.domain.usecase.folder.UpdateFolderUseCase
import com.taetae98.diary.feature.common.Parameter
import com.taetae98.diary.feature.common.util.isNullOrFalse
import com.taetae98.diary.feature.file.event.FolderDetailEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class FolderDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val insertFolderUseCase: InsertFolderUseCase,
    private val findFolderByIdUseCase: FindFolderByIdUseCase,
    private val updateFolderUseCase: UpdateFolderUseCase,
) : ViewModel() {
    val event = MutableSharedFlow<FolderDetailEvent>()

    private val id = MutableStateFlow(savedStateHandle[Parameter.ID] ?: 0L)
    private val parentId =
        MutableStateFlow(savedStateHandle.get<Long>(Parameter.PARENT_ID)?.takeIf { it != 0L })

    val title = MutableStateFlow(savedStateHandle[Parameter.TITLE] ?: "")
    val password = MutableStateFlow(savedStateHandle[Parameter.PASSWORD] ?: "")
    val hasPassword = MutableStateFlow(savedStateHandle[Parameter.HAS_PASSWORD] ?: false)

    init {
        if (savedStateHandle.get<Boolean>(Parameter.IS_INITIALIZED).isNullOrFalse() && id.value != 0L) {
            viewModelScope.launch {
                findFolderByIdUseCase(FindFolderByIdUseCase.Id(id.value)).onSuccess {
                    setParentId(it.parentId)
                    setTitle(it.title)
                    setPassword(it.password ?: "")
                    setHasPassword(it.password != null)
                    savedStateHandle[Parameter.IS_INITIALIZED] = true
                }.onFailure {
                    event.emit(FolderDetailEvent.Error(it))
                }
            }
        }
    }

    private fun setParentId(value: Long?) {
        viewModelScope.launch {
            parentId.emit(value)
            savedStateHandle[Parameter.PARENT_ID] = value
        }
    }

    fun setTitle(value: String) {
        viewModelScope.launch {
            title.emit(value)
            savedStateHandle[Parameter.TITLE] = value
        }
    }

    fun setPassword(value: String) {
        viewModelScope.launch {
            password.emit(value)
            savedStateHandle[Parameter.PASSWORD] = value
        }
    }

    fun setHasPassword(value: Boolean) {
        viewModelScope.launch {
            hasPassword.emit(value)
            savedStateHandle[Parameter.HAS_PASSWORD] = value
        }
    }

    fun edit() {
        if (title.value.isEmpty()) {
            viewModelScope.launch { event.emit(FolderDetailEvent.NoTitle) }
            return
        }

        if (isUpdateMode()) update()
        else insert()
    }

    private fun insert() {
        viewModelScope.launch {
            insertFolderUseCase(
                buildEntity()
            ).onSuccess {
                event.emit(FolderDetailEvent.Edit)
            }.onFailure {
                event.emit(FolderDetailEvent.Error(it))
            }
        }
    }

    private fun update() {
        viewModelScope.launch {
            updateFolderUseCase(
                buildEntity()
            ).onSuccess {
                event.emit(FolderDetailEvent.Edit)
            }.onFailure {
                event.emit(FolderDetailEvent.Error(it))
            }
        }
    }

    private fun buildEntity() = FolderEntity(
        id = id.value,
        parentId = parentId.value,
        title = title.value,
        password = password.value.takeIf { hasPassword.value }
    )

    fun isUpdateMode() = id.value != 0L
}