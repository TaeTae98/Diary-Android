package com.taetae98.diary.feature.place.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.taetae98.diary.domain.usecase.place.PagingPlaceSearchUseCase
import com.taetae98.diary.feature.common.Const
import com.taetae98.diary.feature.common.Parameter
import com.taetae98.diary.feature.place.event.PlaceSearchEvent
import com.taetae98.diary.feature.place.model.PlaceUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class PlaceSearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val pagingPlaceSearchUseCase: PagingPlaceSearchUseCase,
) : ViewModel() {
    val event = MutableSharedFlow<PlaceSearchEvent>()

    val input = MutableStateFlow(savedStateHandle[Parameter.INPUT] ?: "")
    val query = input
        .debounce(Const.USER_INPUT_DELAY)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = input.value
        )

    fun setInput(value: String) {
        viewModelScope.launch {
            input.emit(value)
            savedStateHandle[Parameter.INPUT] = value
        }
    }

    fun search(query: String = input.value) =
        pagingPlaceSearchUseCase(
            PagingPlaceSearchUseCase.Query(
                query = query
            )
        ).getOrElse {
            viewModelScope.launch {
                event.emit(PlaceSearchEvent.Error(it))
            }
            emptyFlow()
        }.map { pagingData ->
            pagingData.map {
                PlaceUiState.from(
                    entity = it,
                    onClick = {
                        viewModelScope.launch {
                            event.emit(PlaceSearchEvent.Search(it))
                        }
                    }
                )
            }
        }.cachedIn(viewModelScope)
}