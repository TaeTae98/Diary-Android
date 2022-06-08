package com.taetae98.diary.feature.place.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taetae98.diary.domain.model.place.PlaceEntity
import com.taetae98.diary.domain.usecase.place.FindPlaceByIdUseCase
import com.taetae98.diary.domain.usecase.place.InsertPlaceUseCase
import com.taetae98.diary.feature.common.Parameter
import com.taetae98.diary.feature.common.util.isFalse
import com.taetae98.diary.feature.common.util.isNullOrFalse
import com.taetae98.diary.feature.place.event.PlaceDetailEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class PlaceDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val insertPlaceUseCase: InsertPlaceUseCase,
    private val findPlaceByIdUseCase: FindPlaceByIdUseCase
) : ViewModel() {
    private val id = MutableStateFlow(savedStateHandle[Parameter.ID] ?: 0L)
    private val latitude = MutableStateFlow(savedStateHandle[Parameter.LATITUDE] ?: 0.0)
    private val longitude = MutableStateFlow(savedStateHandle[Parameter.LONGITUDE] ?: 0.0)

    val event = MutableSharedFlow<PlaceDetailEvent>()

    val title = MutableStateFlow(savedStateHandle[Parameter.TITLE] ?: "")
    val address = MutableStateFlow(savedStateHandle[Parameter.ADDRESS] ?: "")
    val link = MutableStateFlow(savedStateHandle[Parameter.LINK] ?: "")
    val description = MutableStateFlow(savedStateHandle[Parameter.DESCRIPTION] ?: "")
    val hasPassword = MutableStateFlow(savedStateHandle[Parameter.HAS_PASSWORD] ?: false)
    val password = MutableStateFlow(savedStateHandle[Parameter.PASSWORD] ?: "")

    val pin = MutableStateFlow<PlaceEntity?>(savedStateHandle[Parameter.PLACE])

    init {
        if (savedStateHandle.get<Boolean>(Parameter.IS_INITIALIZED).isNullOrFalse()) {
            viewModelScope.launch {
                findPlaceByIdUseCase(FindPlaceByIdUseCase.Id(id.value)).onSuccess {
                    update(it)
                    savedStateHandle[Parameter.IS_INITIALIZED] = true
                }.onFailure {
                    event.emit(PlaceDetailEvent.Error(it))
                }
            }
        }
    }

    fun isEditMode(): Boolean {
        return id.value != 0L
    }

    fun setTitle(value: String) {
        viewModelScope.launch {
            title.emit(value)
            savedStateHandle[Parameter.TITLE] = value
        }
    }

    fun setAddress(value: String) {
        viewModelScope.launch {
            address.emit(value)
            savedStateHandle[Parameter.ADDRESS] = value
        }
    }

    fun setLink(value: String) {
        viewModelScope.launch {
            link.emit(value)
            savedStateHandle[Parameter.LINK] = value
        }
    }

    fun setDescription(value: String) {
        viewModelScope.launch {
            description.emit(value)
            savedStateHandle[Parameter.DESCRIPTION] = value
        }
    }

    fun setHasPassword(value: Boolean) {
        viewModelScope.launch {
            hasPassword.emit(value)
            savedStateHandle[Parameter.HAS_PASSWORD] = value
        }
    }

    fun setPassword(value: String) {
        viewModelScope.launch {
            password.emit(value)
            savedStateHandle[Parameter.PASSWORD] = value
        }
    }

    private fun setLatitude(value: Double) {
        viewModelScope.launch {
            latitude.emit(value)
            savedStateHandle[Parameter.LATITUDE] = value
        }
    }

    private fun setLongitude(value: Double) {
        viewModelScope.launch {
            longitude.emit(value)
            savedStateHandle[Parameter.LONGITUDE] = value
        }
    }

    private fun setPin(value: PlaceEntity?) {
        if (value?.latitude == 0.0 && value.longitude == 0.0) {
            return
        }

        viewModelScope.launch {
            pin.emit(value)
            savedStateHandle[Parameter.PLACE] = value
        }
    }

    fun update(entity: PlaceEntity) {
        setTitle(entity.title)
        setAddress(entity.address)
        setLink(entity.link ?: "")
        setDescription(entity.description)
        setHasPassword(entity.password != null)
        setPassword(entity.password ?: "")
        setLatitude(entity.latitude)
        setLongitude(entity.longitude)
        setPin(entity)
    }

    fun edit() {
        if (title.value.isEmpty()) {
            viewModelScope.launch { event.emit(PlaceDetailEvent.NoTitle) }
            return
        }

        if (hasPlace().isFalse()) {
            viewModelScope.launch { event.emit(PlaceDetailEvent.NoPlace) }
            return
        }

        viewModelScope.launch {
            val entity = PlaceEntity(
                id = id.value,
                title = title.value,
                address = address.value,
                link = link.value.takeIf { it.isNotBlank() },
                description = description.value,
                password = password.value.takeIf { hasPassword.value },
                latitude = latitude.value,
                longitude = longitude.value
            )

            insertPlaceUseCase(
                entity
            ).onSuccess {
                event.emit(PlaceDetailEvent.Edit(entity.copy(id = it)))
            }.onFailure {
                event.emit(PlaceDetailEvent.Error(it))
            }
        }
    }

    private fun hasPlace() = latitude.value != 0.0 && longitude.value != 0.0
}