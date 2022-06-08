package com.taetae98.diary.feature.file

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taetae98.diary.domain.model.file.FileEntity
import com.taetae98.diary.domain.usecase.file.InsertFileUseCase
import com.taetae98.diary.domain.usecase.file.PagingFileByTagIdsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

@HiltViewModel
class FileViewModel @Inject constructor(
    private val insertFileUseCase: InsertFileUseCase,
    pagingFileByTagIdsUseCase: PagingFileByTagIdsUseCase
) : ViewModel() {
    val event = MutableSharedFlow<FileEvent>()

    val pagingData = pagingFileByTagIdsUseCase(
        PagingFileByTagIdsUseCase.Ids(emptyList())
    ).getOrElse {
        viewModelScope.launch { event.emit(FileEvent.Fail) }
        emptyFlow()
    }

    fun save(uri: Uri) = viewModelScope.launch {
        insertFileUseCase(
            InsertFileUseCase.Params(
                FileEntity(
                    title = "Hello",
                    description = "ZZZ"
                ), uri
            )
        ).onSuccess {
            event.emit(FileEvent.Success)
        }.onFailure {
            event.emit(FileEvent.Fail)
        }
    }

    fun createFolder(name: String) {

    }
}