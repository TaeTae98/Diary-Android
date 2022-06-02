package com.taetae98.diary.feature.place.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taetae98.diary.domain.model.PlaceEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class PlaceViewModel @Inject constructor(

) : ViewModel() {
    val pin = MutableStateFlow<PlaceEntity?>(null)

    fun setPin(value: PlaceEntity?) {
        viewModelScope.launch { pin.emit(value) }
    }
}