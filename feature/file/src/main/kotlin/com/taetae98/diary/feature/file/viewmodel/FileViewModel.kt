package com.taetae98.diary.feature.file.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.taetae98.diary.domain.model.file.FolderEntity
import com.taetae98.diary.domain.usecase.file.PagingFileByFolderIdAndTagIdsUseCase
import com.taetae98.diary.domain.usecase.folder.DeleteFolderByIdUseCase
import com.taetae98.diary.domain.usecase.folder.FindFolderFlowByIdUseCase
import com.taetae98.diary.domain.usecase.folder.PagingFolderByParentIdUseCase
import com.taetae98.diary.feature.file.event.FileEvent
import com.taetae98.diary.feature.file.model.FilePreviewUiState
import com.taetae98.diary.feature.file.model.FolderPreviewUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class FileViewModel @Inject constructor(
    private val pagingFileByFolderIdAndTagIdsUseCase: PagingFileByFolderIdAndTagIdsUseCase,
    private val pagingFolderByParentIdUseCase: PagingFolderByParentIdUseCase,
    private val findFolderFlowByIdUseCase: FindFolderFlowByIdUseCase,
    private val deleteFolderByIdUseCase: DeleteFolderByIdUseCase,
) : ViewModel() {
    val event = MutableSharedFlow<FileEvent>()

    private val id = MutableStateFlow<Long?>(null)

    val folder = id.flatMapLatest {
        if (it == null) {
            flow { emit(null) }
        } else {
            findFolderFlowByIdUseCase(FindFolderFlowByIdUseCase.Id(it)).getOrElse {
                flow<FolderEntity?> { emit(null) }
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = null
    )

    val folderPaging = id.flatMapLatest {
        pagingFolderByParentIdUseCase(
            PagingFolderByParentIdUseCase.Id(it)
        ).getOrElse {
            viewModelScope.launch { event.emit(FileEvent.Error(it)) }
            emptyFlow()
        }
    }.map { pagingData ->
        pagingData.map {
            FolderPreviewUiState.from(
                entity = it,
                onClick = {
                    val password = it.password
                    if (password == null) {
                        navigate(it.id)
                    } else {
                        viewModelScope.launch {
                            event.emit(
                                FileEvent.SecurityAction(password = password) {
                                    navigate(it.id)
                                }
                            )
                        }
                    }
                }
            )
        }
    }.cachedIn(viewModelScope)

    val filePaging = id.flatMapLatest {
        pagingFileByFolderIdAndTagIdsUseCase(
            PagingFileByFolderIdAndTagIdsUseCase.Parameter(
                folderId = it,
                tagIds = emptyList()
            )
        ).getOrElse {
            viewModelScope.launch { event.emit(FileEvent.Error(it)) }
            emptyFlow()
        }
    }.map { pagingData ->
        pagingData.map {
            FilePreviewUiState.from(
                entity = it
            )
        }
    }.cachedIn(viewModelScope)

    fun deleteFolder() {
        id.value?.let { id ->
            viewModelScope.launch {
                deleteFolderByIdUseCase(DeleteFolderByIdUseCase.Id(id)).onSuccess {
                    navigate(folder.value?.parentId)
                }.onFailure {
                    event.emit(FileEvent.Error(it))
                }
            }
        }
    }

    fun navigate(value: Long?) {
        viewModelScope.launch {
            id.emit(value)
        }
    }
}