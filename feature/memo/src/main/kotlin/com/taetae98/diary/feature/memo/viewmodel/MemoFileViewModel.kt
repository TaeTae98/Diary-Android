package com.taetae98.diary.feature.memo.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.taetae98.diary.domain.usecase.file.PagingFileByFolderIdAndTagIdsUseCase
import com.taetae98.diary.domain.usecase.folder.FindFolderFlowByIdUseCase
import com.taetae98.diary.domain.usecase.folder.PagingFolderByParentIdUseCase
import com.taetae98.diary.feature.common.Const
import com.taetae98.diary.feature.common.Parameter
import com.taetae98.diary.feature.compose.file.FolderUiState
import com.taetae98.diary.feature.memo.event.MemoFileEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class MemoFileViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val findFolderFlowByIdUseCase: FindFolderFlowByIdUseCase,
    private val pagingFolderByParentIdUseCase: PagingFolderByParentIdUseCase,
    private val pagingFileByFolderIdAndTagIdsUseCase: PagingFileByFolderIdAndTagIdsUseCase,
) : ViewModel() {
    val event = MutableSharedFlow<MemoFileEvent>()
    val folderId = MutableStateFlow(savedStateHandle.get<Long>(Parameter.FOLDER_ID))
    val folder = folderId.flatMapLatest { folderId ->
        if (folderId == null) {
            flowOf(null)
        } else {
            findFolderFlowByIdUseCase(
                FindFolderFlowByIdUseCase.Id(folderId)
            ).getOrElse {
                emitEvent(MemoFileEvent.Error(it))
                emptyFlow()
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = Const.STATE_FLOW_DELAY),
        null
    )

    val folderPaging = folderId.flatMapLatest { folderId ->
        pagingFolderByParentIdUseCase(
            PagingFolderByParentIdUseCase.Id(folderId)
        ).getOrElse {
            emitEvent(MemoFileEvent.Error(it))
            emptyFlow()
        }
    }.map { pagingData ->
        pagingData.map {
            FolderUiState(
                title = it.title,
                onClick = { setFolderId(it.id) }
            )
        }
    }.cachedIn(viewModelScope)

    val filePaging = folderId.flatMapLatest { folderId ->
        pagingFileByFolderIdAndTagIdsUseCase(
            PagingFileByFolderIdAndTagIdsUseCase.Parameter(
                folderId = folderId,
                tagIds = emptyList()
            )
        ).getOrElse {
            emitEvent(MemoFileEvent.Error(it))
            emptyFlow()
        }
    }.map { pagingData ->
        pagingData.map {

        }
    }.cachedIn(viewModelScope)

    private fun emitEvent(value: MemoFileEvent) = viewModelScope.launch {
        event.emit(value)
    }

    private fun setFolderId(value: Long?) = viewModelScope.launch {
        folderId.emit(value)
        savedStateHandle[Parameter.FOLDER_ID] = value
    }

    fun navigateUp() {
        setFolderId(folder.value?.parentId)
    }
}