package com.taetae98.diary.feature.developer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taetae98.diary.domain.usecase.file.developer.GetFileListUseCase
import com.taetae98.diary.domain.usecase.file.developer.IsWarningFileUseCase
import com.taetae98.diary.feature.developer.event.FileExplorerEvent
import com.taetae98.diary.feature.developer.model.FileExplorerUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class FileExplorerViewModel @Inject constructor(
    private val getFileListUseCase: GetFileListUseCase,
    private val isWarningFileUseCase: IsWarningFileUseCase,
) : ViewModel() {
    val event = MutableSharedFlow<FileExplorerEvent>()
    private val fileSignal = MutableStateFlow(System.currentTimeMillis())
    val file = fileSignal.map {
        getFileListUseCase().getOrElse {
            event.emit(FileExplorerEvent.Error(it))
            emptyList()
        }.map { file ->
            FileExplorerUiState(
                file = file,
                isWarning = isWarningFileUseCase(file).getOrElse {
                    event.emit(FileExplorerEvent.Error(it))
                    true
                }
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    fun fix() {
        viewModelScope.launch {
            file.value.filter {
                it.isWarning
            }.onEach {
                it.file.deleteRecursively()
            }

            fileSignal.emit(System.currentTimeMillis())
        }
    }
}