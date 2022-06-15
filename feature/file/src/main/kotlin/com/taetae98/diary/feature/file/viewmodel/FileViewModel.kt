package com.taetae98.diary.feature.file.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.taetae98.diary.domain.model.file.FileEntity
import com.taetae98.diary.domain.model.file.FolderEntity
import com.taetae98.diary.domain.usecase.file.DeleteFileUseCase
import com.taetae98.diary.domain.usecase.file.ExplorerFileUseCase
import com.taetae98.diary.domain.usecase.file.ExportFileUseCase
import com.taetae98.diary.domain.usecase.file.PagingFileByFolderIdAndTagIdsUseCase
import com.taetae98.diary.domain.usecase.file.UpdateFileFolderIdUseCase
import com.taetae98.diary.domain.usecase.folder.FindFolderFlowByIdUseCase
import com.taetae98.diary.domain.usecase.folder.PagingFolderByParentIdUseCase
import com.taetae98.diary.feature.common.Parameter
import com.taetae98.diary.feature.compose.file.FilePreviewUiState
import com.taetae98.diary.feature.file.event.FileEvent
import com.taetae98.diary.feature.file.model.FileViewMode
import com.taetae98.diary.feature.file.model.FolderPreviewUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class FileViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val pagingFileByFolderIdAndTagIdsUseCase: PagingFileByFolderIdAndTagIdsUseCase,
    private val pagingFolderByParentIdUseCase: PagingFolderByParentIdUseCase,
    private val findFolderFlowByIdUseCase: FindFolderFlowByIdUseCase,
    private val deleteFileUseCase: DeleteFileUseCase,
    private val updateFileFolderIdUseCase: UpdateFileFolderIdUseCase,
    private val explorerFileUseCase: ExplorerFileUseCase,
    private val exportFileUseCase: ExportFileUseCase,
) : ViewModel() {
    val event = MutableSharedFlow<FileEvent>()

    private val id = MutableStateFlow(savedStateHandle.get<Long>(Parameter.ID))
    val viewMode = MutableStateFlow(savedStateHandle[Parameter.VIEW_MODE] ?: FileViewMode.VIEW)
    val selectedFolder = MutableStateFlow(
        savedStateHandle.get<Collection<Long>>(Parameter.SELECTED_FOLDER) ?: emptyList()
    )
    val selectedFile = MutableStateFlow(
        savedStateHandle.get<Collection<Long>>(Parameter.SELECTED_FILE) ?: emptyList()
    )


    val folder = id.flatMapLatest { id ->
        if (id == null) {
            flow { emit(null) }
        } else {
            findFolderFlowByIdUseCase(FindFolderFlowByIdUseCase.Id(id)).getOrElse {
                emitEvent(FileEvent.Error(it))
                flow<FolderEntity?> { emit(null) }
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = null
    )

    val folderPaging = id.flatMapLatest { id ->
        pagingFolderByParentIdUseCase(
            PagingFolderByParentIdUseCase.Id(id)
        ).getOrElse {
            emitEvent(FileEvent.Error(it))
            emptyFlow()
        }
    }.map { pagingData ->
        pagingData.map {
            FolderPreviewUiState(
                entity = it,
                onClick = {
                    val password = it.password
                    val onAction = {
                        when (viewMode.value) {
                            FileViewMode.SELECT -> toggleSelectMode(it)
                            FileViewMode.MOVE -> {
                                if (selectedFolder.value.contains(it.id)) toggleSelectMode(it)
                                else navigate(it.id)
                            }
                            else -> navigate(it.id)
                        }
                    }
                    if (password == null) {
                        onAction()
                    } else {
                        emitEvent(
                            FileEvent.SecurityAction(
                                password = password,
                                onAction = onAction
                            )
                        )
                    }
                },
                onLongClick = {
                    val password = it.password
                    val onAction = {
                        if (viewMode.value == FileViewMode.VIEW) {
                            setViewMode(FileViewMode.SELECT)
                        }
                        toggleSelectMode(it)
                    }
                    if (password == null) {
                        onAction()
                    } else {
                        emitEvent(
                            FileEvent.SecurityAction(
                                password = password,
                                onAction = onAction
                            )
                        )
                    }
                },
                onSelect = { toggleSelectMode(it) }
            )
        }
    }.cachedIn(viewModelScope)

    val filePaging = id.flatMapLatest { id ->
        pagingFileByFolderIdAndTagIdsUseCase(
            PagingFileByFolderIdAndTagIdsUseCase.Parameter(
                folderId = id,
                tagIds = emptyList()
            )
        ).getOrElse {
            emitEvent(FileEvent.Error(it))
            emptyFlow()
        }
    }.map { pagingData ->
        pagingData.map {
            FilePreviewUiState(
                entity = it,
                onClick = {
                    val password = it.password
                    val onAction = {
                        when (viewMode.value) {
                            FileViewMode.VIEW -> explore(it)
                            else -> toggleSelectMode(it)
                        }
                    }

                    if (password == null) {
                        onAction()
                    } else {
                        emitEvent(
                            FileEvent.SecurityAction(
                                password = password,
                                onAction = onAction
                            )
                        )
                    }
                },
                onLongClick = {
                    val password = it.password
                    val onAction = {
                        if (viewMode.value == FileViewMode.VIEW) {
                            setViewMode(FileViewMode.SELECT)
                        }
                        toggleSelectMode(it)
                    }
                    if (password == null) {
                        onAction()
                    } else {
                        emitEvent(
                            FileEvent.SecurityAction(
                                password = password,
                                onAction = onAction
                            )
                        )
                    }
                }
            )
        }
    }.cachedIn(viewModelScope)

    val isBackKeyEnable = viewMode.combine(id) { viewMode, id ->
        viewMode != FileViewMode.VIEW || id != null
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = viewMode.value != FileViewMode.VIEW || id.value != null
    )

    fun paste() {
        viewModelScope.launch {
            updateFileFolderIdUseCase(
                UpdateFileFolderIdUseCase.Parameter(
                    targetId = id.value,
                    fileId = selectedFile.value,
                    folderId = selectedFolder.value
                )
            ).onSuccess {
                setViewMode(FileViewMode.VIEW)
            }.onFailure {
                emitEvent(FileEvent.Error(it))
            }
        }
    }

    fun back() {
        when (viewMode.value) {
            FileViewMode.SELECT -> setViewMode(FileViewMode.VIEW)
            else -> navigate(folder.value?.parentId)
        }
    }

    fun navigate(value: Long?) {
        viewModelScope.launch {
            id.emit(value)
            savedStateHandle[Parameter.ID] = value
        }
    }

    fun delete() {
        viewModelScope.launch {
            deleteFileUseCase(
                DeleteFileUseCase.Parameter(
                    folderIds = selectedFolder.value,
                    fileIds = selectedFile.value
                )
            ).onSuccess {
                setViewMode(FileViewMode.VIEW)
            }.onFailure {
                event.emit(FileEvent.Error(it))
            }
        }
    }

    fun export() {
        viewModelScope.launch {
            exportFileUseCase(
                ExportFileUseCase.Parameter(
                    folder = selectedFolder.value,
                    file = selectedFile.value
                )
            ).onSuccess {
                setViewMode(FileViewMode.VIEW)
            }.onFailure {
                event.emit(FileEvent.Error(it))
            }
        }
    }

    fun setViewMode(value: FileViewMode) {
        viewModelScope.launch {
            viewMode.emit(value)
            savedStateHandle[Parameter.VIEW_MODE] = value

            if (value == FileViewMode.VIEW) {
                setSelectedFolder(emptyList())
                setSelectedFile(emptyList())
            }
        }
    }

    private fun emitEvent(value: FileEvent) {
        viewModelScope.launch {
            event.emit(value)
        }
    }

    private fun explore(entity: FileEntity) {
        viewModelScope.launch {
            explorerFileUseCase(entity).onFailure {
                emitEvent(FileEvent.Error(it))
            }
        }
    }

    private fun toggleSelectMode(entity: FolderEntity) {
        setSelectedFolder(
            HashSet(selectedFolder.value).apply {
                if (contains(entity.id)) remove(entity.id)
                else add(entity.id)
            }
        )
    }

    private fun toggleSelectMode(entity: FileEntity) {
        setSelectedFile(
            HashSet(selectedFile.value).apply {
                if (contains(entity.id)) remove(entity.id)
                else add(entity.id)
            }
        )
    }

    private fun setSelectedFolder(collection: Collection<Long>) {
        viewModelScope.launch {
            selectedFolder.emit(collection)
            savedStateHandle[Parameter.SELECTED_FOLDER] = collection
        }
    }

    private fun setSelectedFile(collection: Collection<Long>) {
        viewModelScope.launch {
            selectedFile.emit(collection)
            savedStateHandle[Parameter.SELECTED_FILE] = collection
        }
    }
}