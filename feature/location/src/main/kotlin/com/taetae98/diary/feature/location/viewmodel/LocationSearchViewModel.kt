package com.taetae98.diary.feature.location.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.taetae98.diary.domain.usecase.location.DeleteLocationSearchQueryUseCase
import com.taetae98.diary.domain.usecase.location.InsertLocationSearchQueryUseCase
import com.taetae98.diary.domain.usecase.location.PagingLocationSearchQueryUseCase
import com.taetae98.diary.feature.location.event.LocationSearchEvent
import com.taetae98.diary.feature.location.model.LocationSearchQueryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class LocationSearchViewModel @Inject constructor(
    pagingLocationSearchQueryUseCase: PagingLocationSearchQueryUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val insertLocationSearchQueryUseCase: InsertLocationSearchQueryUseCase,
    private val deleteLocationSearchQueryUseCase: DeleteLocationSearchQueryUseCase
) : ViewModel() {
    val event = MutableSharedFlow<LocationSearchEvent>()
    val input = MutableStateFlow(savedStateHandle[QUERY] ?: "")
    val paging = pagingLocationSearchQueryUseCase().getOrElse {
        viewModelScope.launch { event.emit(LocationSearchEvent.Error(it)) }
        emptyFlow()
    }.map { pagingData ->
        pagingData.map {
            LocationSearchQueryUiState.from(
                entity = it,
                onDelete = { delete(it.id) }
            )
        }
    }.cachedIn(viewModelScope)

    fun setInput(value: String) {
        viewModelScope.launch {
            input.emit(value)
        }
    }

    fun search(query: String = input.value) {
        viewModelScope.launch {
            insertLocationSearchQueryUseCase(
                InsertLocationSearchQueryUseCase.Query(query)
            ).onSuccess {
                event.emit(LocationSearchEvent.Search(query))
            }.onFailure {
                event.emit(LocationSearchEvent.Error(it))
            }
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch {
            deleteLocationSearchQueryUseCase(
                DeleteLocationSearchQueryUseCase.Id(id)
            ).onSuccess {
                event.emit(LocationSearchEvent.Delete(it))
            }.onFailure {
                event.emit(LocationSearchEvent.Error(it))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        savedStateHandle[QUERY] = input.value
    }

    companion object {
        private const val QUERY = "QUERY"
    }
}