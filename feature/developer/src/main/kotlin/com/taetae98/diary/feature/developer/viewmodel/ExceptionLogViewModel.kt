package com.taetae98.diary.feature.developer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.taetae98.diary.domain.model.exception.ExceptionRelation
import com.taetae98.diary.domain.usecase.exception.DeleteAllExceptionUseCase
import com.taetae98.diary.domain.usecase.exception.DeleteExceptionByIdUseCase
import com.taetae98.diary.domain.usecase.exception.PagingExceptionUseCase
import com.taetae98.diary.domain.usecase.exception.RestoreExceptionRelationUseCase
import com.taetae98.diary.feature.developer.event.ExceptionLogEvent
import com.taetae98.diary.feature.developer.model.ExceptionLogUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class ExceptionLogViewModel @Inject constructor(
    pagingExceptionUseCase: PagingExceptionUseCase,
    private val deleteExceptionByIdUseCase: DeleteExceptionByIdUseCase,
    private val restoreExceptionRelationUseCase: RestoreExceptionRelationUseCase,
    private val deleteAllExceptionUseCase: DeleteAllExceptionUseCase,
) : ViewModel() {
    val event = MutableSharedFlow<ExceptionLogEvent>()
    val paging = pagingExceptionUseCase().getOrElse {
        viewModelScope.launch { event.emit(ExceptionLogEvent.Error(it)) }
        emptyFlow()
    }.map { pagingData ->
        pagingData.map {
            ExceptionLogUiState(
                entity = it,
                onDelete = { deleteById(it.id) }
            )
        }
    }.cachedIn(viewModelScope)

    private fun deleteById(id: Long) {
        viewModelScope.launch {
            deleteExceptionByIdUseCase(
                DeleteExceptionByIdUseCase.Id(id)
            ).onSuccess {
                event.emit(
                    ExceptionLogEvent.DeleteExceptionLog(
                        collection = listOf(it),
                        onRestore = { restore(listOf(it)) }
                    )
                )
            }.onFailure {
                event.emit(ExceptionLogEvent.Error(it))
            }
        }
    }

    private fun restore(collection: Collection<ExceptionRelation>) {
        viewModelScope.launch {
            restoreExceptionRelationUseCase(
                collection
            ).onFailure {
                event.emit(ExceptionLogEvent.Error(it))
            }
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            deleteAllExceptionUseCase().onSuccess {
                event.emit(
                    ExceptionLogEvent.DeleteExceptionLog(
                        collection = it,
                        onRestore = { restore(it) }
                    )
                )
            }.onFailure {
                event.emit(ExceptionLogEvent.Error(it))
            }
        }
    }
}