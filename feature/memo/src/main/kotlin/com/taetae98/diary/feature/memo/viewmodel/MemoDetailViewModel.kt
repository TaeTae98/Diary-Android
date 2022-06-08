package com.taetae98.diary.feature.memo.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taetae98.diary.domain.model.place.PlaceEntity
import com.taetae98.diary.domain.model.memo.MemoEntity
import com.taetae98.diary.domain.model.memo.MemoRelation
import com.taetae98.diary.domain.usecase.memo.FindMemoRelationByIdUseCase
import com.taetae98.diary.domain.usecase.memo.InsertMemoRelationUseCase
import com.taetae98.diary.feature.common.Parameter
import com.taetae98.diary.feature.common.util.isNullOrFalse
import com.taetae98.diary.feature.memo.event.MemoDetailEvent
import com.taetae98.diary.feature.memo.model.PlaceUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class MemoDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val findMemoRelationByIdUseCase: FindMemoRelationByIdUseCase,
    private val insertMemoRelationUseCase: InsertMemoRelationUseCase,
) : ViewModel() {
    val event = MutableSharedFlow<MemoDetailEvent>()

    private val id = MutableStateFlow(savedStateHandle[Parameter.ID] ?: 0L)
    val title = MutableStateFlow(savedStateHandle[Parameter.TITLE] ?: "")
    val description = MutableStateFlow(savedStateHandle[Parameter.DESCRIPTION] ?: "")
    val password = MutableStateFlow(savedStateHandle[Parameter.PASSWORD] ?: "")
    val hasPassword = MutableStateFlow(savedStateHandle[Parameter.HAS_PASSWORD] ?: false)

    val place = MutableStateFlow<Collection<PlaceEntity>>(emptyList())
    val camera = MutableStateFlow(place.value.lastOrNull())
    val placeUiState = place.map { collection ->
        collection.map {
            PlaceUiState.from(
                entity = it,
                onClick = { setCamera(it) },
                onDelete = { delete(it) }
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    init {
        if (savedStateHandle.get<Boolean>(Parameter.IS_INITIALIZED).isNullOrFalse()) {
            viewModelScope.launch {
                findMemoRelationByIdUseCase(FindMemoRelationByIdUseCase.Id(id.value)).onSuccess {
                    setTitle(it.memo.title)
                    setDescription(it.memo.description)
                    setPassword(it.memo.password ?: "")
                    setHasPassword(it.memo.password != null)
                    setPlace(it.place)
                    setCamera(it.place.lastOrNull())
                    savedStateHandle[Parameter.IS_INITIALIZED] = true
                }.onFailure {
                    event.emit(MemoDetailEvent.Error(it))
                }
            }
        }
    }

    fun setTitle(value: String) {
        viewModelScope.launch {
            title.emit(value)
            savedStateHandle[Parameter.TITLE] = value
        }
    }

    fun setDescription(value: String) {
        viewModelScope.launch {
            description.emit(value)
            savedStateHandle[Parameter.DESCRIPTION] = value
        }
    }

    fun setPassword(value: String) {
        viewModelScope.launch {
            password.emit(value)
            savedStateHandle[Parameter.PASSWORD] = value
        }
    }

    fun setHasPassword(value: Boolean) {
        viewModelScope.launch {
            hasPassword.emit(value)
            savedStateHandle[Parameter.HAS_PASSWORD] = value
        }
    }

    fun edit() {
        viewModelScope.launch(Dispatchers.IO) {
            if (title.value.isEmpty()) {
                event.emit(MemoDetailEvent.NoTitle)
            } else {
                insertMemoRelationUseCase(
                    MemoRelation(
                        memo = MemoEntity(
                            id = id.value,
                            title = title.value,
                            description = description.value,
                            password = password.value.takeIf { hasPassword.value }
                        ),
                        place = place.value.toList()
                    )
                ).onSuccess {
                    event.emit(MemoDetailEvent.Edit)
                }.onFailure {
                    event.emit(MemoDetailEvent.Error(it))
                }
            }
        }
    }

    fun clear() {
        setTitle("")
        setDescription("")
        setPassword("")
        setHasPassword(false)
        setPlace(emptyList())
        setCamera(null)
    }

    private fun setPlace(value: Collection<PlaceEntity>) {
        viewModelScope.launch {
            place.emit(value)
            camera.emit(value.lastOrNull())
        }
    }

    private fun setCamera(value: PlaceEntity?) {
        viewModelScope.launch {
            camera.emit(value)
        }
    }

    fun add(value: PlaceEntity) {
        setPlace(LinkedHashSet(place.value).apply { add(value) })
    }

    private fun delete(value: PlaceEntity) {
        setPlace(LinkedHashSet(place.value).apply { remove(value) })
    }

    fun isEditMode(): Boolean {
        return id.value != 0L
    }
}