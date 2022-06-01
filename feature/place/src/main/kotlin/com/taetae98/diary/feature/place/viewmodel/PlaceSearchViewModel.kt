package com.taetae98.diary.feature.place.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.taetae98.diary.domain.usecase.place.DeletePlaceSearchQueryUseCase
import com.taetae98.diary.domain.usecase.place.InsertPlaceSearchQueryUseCase
import com.taetae98.diary.domain.usecase.place.PagingPlaceSearchQueryUseCase
import com.taetae98.diary.feature.place.event.PlaceSearchEvent
import com.taetae98.diary.feature.place.model.PlaceSearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class PlaceSearchViewModel @Inject constructor(
    pagingPlaceSearchQueryUseCase: PagingPlaceSearchQueryUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val insertPlaceSearchQueryUseCase: InsertPlaceSearchQueryUseCase,
    private val deletePlaceSearchQueryUseCase: DeletePlaceSearchQueryUseCase
) : ViewModel() {
    val event = MutableSharedFlow<PlaceSearchEvent>()
    val input = MutableStateFlow(savedStateHandle[QUERY] ?: "")
    val paging = pagingPlaceSearchQueryUseCase().getOrElse {
        viewModelScope.launch { event.emit(PlaceSearchEvent.Error(it)) }
        emptyFlow()
    }.map { pagingData ->
        pagingData.map {
            PlaceSearchUiState.from(
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
            insertPlaceSearchQueryUseCase(
                InsertPlaceSearchQueryUseCase.Query(query)
            ).onSuccess {
                event.emit(PlaceSearchEvent.Search(query))
            }.onFailure {
                event.emit(PlaceSearchEvent.Error(it))
            }
        }
    }

    private fun delete(id: Long) {
        viewModelScope.launch {
            deletePlaceSearchQueryUseCase(
                DeletePlaceSearchQueryUseCase.Id(id)
            ).onSuccess {
                event.emit(PlaceSearchEvent.Delete(it))
            }.onFailure {
                event.emit(PlaceSearchEvent.Error(it))
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