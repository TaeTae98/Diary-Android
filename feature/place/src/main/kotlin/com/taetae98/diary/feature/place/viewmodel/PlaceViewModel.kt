package com.taetae98.diary.feature.place.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.taetae98.diary.domain.usecase.place.PagingPlaceByTagIdsUseCase
import com.taetae98.diary.feature.place.event.PlaceEvent
import com.taetae98.diary.feature.place.model.PlaceUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class PlaceViewModel @Inject constructor(
    private val pagingPlaceByTagIdsUseCase: PagingPlaceByTagIdsUseCase
) : ViewModel() {
    val event = MutableSharedFlow<PlaceEvent>()

    fun pagingByTagIds(ids: Collection<Long>) = pagingPlaceByTagIdsUseCase(
        PagingPlaceByTagIdsUseCase.Ids(ids)
    ).getOrElse {
        viewModelScope.launch { event.emit(PlaceEvent.Error(it)) }
        emptyFlow()
    }.map { pagingData ->
        pagingData.map {
            PlaceUiState.from(it) {
                viewModelScope.launch { event.emit(PlaceEvent.Detail(it)) }
            }
        }
    }.cachedIn(viewModelScope)
}