package com.taetae98.diary.feature.place.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.taetae98.diary.domain.model.PlaceRelation
import com.taetae98.diary.domain.usecase.place.DeletePlaceByIdUseCase
import com.taetae98.diary.domain.usecase.place.PagingPlaceByTagIdsUseCase
import com.taetae98.diary.domain.usecase.place.RestorePlaceRelationUseCase
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
    private val pagingPlaceByTagIdsUseCase: PagingPlaceByTagIdsUseCase,
    private val deletePlaceByIdUseCase: DeletePlaceByIdUseCase,
    private val restorePlaceRelationUseCase: RestorePlaceRelationUseCase,
) : ViewModel() {
    val event = MutableSharedFlow<PlaceEvent>()

    fun pagingByTagIds(ids: Collection<Long>) = pagingPlaceByTagIdsUseCase(
        PagingPlaceByTagIdsUseCase.Ids(ids)
    ).getOrElse {
        viewModelScope.launch { event.emit(PlaceEvent.Error(it)) }
        emptyFlow()
    }.map { pagingData ->
        pagingData.map {
            PlaceUiState.from(
                entity = it,
                onClick = {
                    viewModelScope.launch {
                        val password = it.password
                        if (password == null) {
                            event.emit(PlaceEvent.Detail(it))
                        } else {
                            event.emit(
                                PlaceEvent.SecurityAction(password = password) {
                                    viewModelScope.launch { event.emit(PlaceEvent.Detail(it)) }
                                }
                            )
                        }
                    }
                },
                onDelete = {
                    val password = it.password
                    if (password == null) {
                        delete(it.id)
                    } else {
                        viewModelScope.launch {
                            event.emit(
                                PlaceEvent.SecurityAction(password = password) {
                                    delete(it.id)
                                }
                            )
                        }
                    }
                }
            )
        }
    }.cachedIn(viewModelScope)

    private fun delete(id: Long) {
        viewModelScope.launch {
            deletePlaceByIdUseCase(DeletePlaceByIdUseCase.Id(id)).onSuccess {
                event.emit(
                    PlaceEvent.Delete(it) { restore(it) }
                )
            }.onFailure {
                event.emit(PlaceEvent.Error(it))
            }
        }
    }

    private fun restore(relation: PlaceRelation) {
        viewModelScope.launch {
            restorePlaceRelationUseCase(relation).onFailure {
                event.emit(PlaceEvent.Error(it))
            }
        }
    }
}