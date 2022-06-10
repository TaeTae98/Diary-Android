package com.taetae98.diary.feature.file.viewmodel

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taetae98.diary.domain.model.file.FileEntity
import com.taetae98.diary.domain.usecase.file.FindFileByIdUseCase
import com.taetae98.diary.domain.usecase.file.InsertFileUseCase
import com.taetae98.diary.domain.usecase.file.UpdateFileUseCase
import com.taetae98.diary.feature.common.Parameter
import com.taetae98.diary.feature.common.util.isNullOrFalse
import com.taetae98.diary.feature.file.event.FileDetailEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class FileDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val insertFileUseCase: InsertFileUseCase,
    private val findFileByIdUseCase: FindFileByIdUseCase,
    private val updateFileUseCase: UpdateFileUseCase
) : ViewModel() {
    val event = MutableSharedFlow<FileDetailEvent>()

    private val fileEntity =
        MutableStateFlow(savedStateHandle[Parameter.FILE_ENTITY] ?: FileEntity())
    private val folderId =
        MutableStateFlow(savedStateHandle.get<Long>(Parameter.FOLDER_ID)?.takeIf { it != 0L })

    val id = MutableStateFlow(savedStateHandle[Parameter.ID] ?: 0L)
    val title = MutableStateFlow(savedStateHandle[Parameter.TITLE] ?: "")
    val description = MutableStateFlow(savedStateHandle[Parameter.DESCRIPTION] ?: "")
    val hasPassword = MutableStateFlow(savedStateHandle[Parameter.HAS_PASSWORD] ?: false)
    val password = MutableStateFlow(savedStateHandle[Parameter.PASSWORD] ?: "")

    init {
        if (savedStateHandle.get<Boolean>(Parameter.IS_INITIALIZED)
                .isNullOrFalse() && id.value != 0L
        ) {
            viewModelScope.launch {
                findFileByIdUseCase(FindFileByIdUseCase.Id(id.value)).onSuccess {
                    setFileEntity(it)
                    setFolderId(it.folderId)
                    setTitle(it.title)
                    setDescription(it.description)
                    setHasPassword(it.password != null)
                    setPassword(it.password ?: "")
                }.onFailure {
                    event.emit(FileDetailEvent.Error(it))
                }
            }
        }
    }

    private fun setFileEntity(value: FileEntity) {
        viewModelScope.launch {
            fileEntity.emit(value)
            savedStateHandle[Parameter.FILE_ENTITY] = value
        }
    }

    private fun setFolderId(value: Long?) {
        viewModelScope.launch {
            folderId.emit(value)
            savedStateHandle[Parameter.FOLDER_ID] = value
        }
    }

    fun setTitle(value: String) {
        viewModelScope.launch {
            title.emit(value)
            savedStateHandle[Parameter.TITLE] = value
        }
    }

    fun setDescription(value: String) {
        viewModelScope.launch {
            description.emit(value)
            savedStateHandle[Parameter.DESCRIPTION] = value
        }
    }

    fun setHasPassword(value: Boolean) {
        viewModelScope.launch {
            hasPassword.emit(value)
            savedStateHandle[Parameter.HAS_PASSWORD] = value
        }
    }

    fun setPassword(value: String) {
        viewModelScope.launch {
            password.emit(value)
            savedStateHandle[Parameter.PASSWORD] = value
        }
    }

    fun insert(uri: Uri) {
        if (isTitleEmpty()) return

        viewModelScope.launch {
            insertFileUseCase(
                InsertFileUseCase.Parameter(
                    entity = buildEntity(),
                    uri = uri
                )
            ).onSuccess {
                event.emit(FileDetailEvent.Insert)
            }.onFailure {
                event.emit(FileDetailEvent.Error(it))
            }
        }
    }

    fun update() {
        if (isTitleEmpty()) return

        viewModelScope.launch {
            updateFileUseCase(
                buildEntity()
            ).onSuccess {
                event.emit(FileDetailEvent.Update)
            }.onFailure {
                event.emit(FileDetailEvent.Error(it))
            }
        }
    }

    fun isTitleEmpty(): Boolean {
        return title.value.isEmpty().also {
            if (it) {
                viewModelScope.launch {
                    event.emit(FileDetailEvent.NoTitle)
                }
            }
        }
    }

    fun clear() {
        setTitle("")
        setDescription("")
        setPassword("")
        setHasPassword(false)
    }

    private fun buildEntity() = fileEntity.value.copy(
        folderId = folderId.value,
        title = title.value,
        description = description.value,
        password = password.value.takeIf { hasPassword.value },
        updatedAt = System.currentTimeMillis()
    )

    fun isUpdateMode() = id.value != 0L
}