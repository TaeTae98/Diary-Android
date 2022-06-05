package com.taetae98.diary.feature.place.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taetae98.diary.feature.compose.diary.DiaryMap
import com.taetae98.diary.feature.compose.place.PlaceHeaderCompose
import com.taetae98.diary.feature.place.model.PlaceUiState

@Composable
fun PlacePreviewCompose(
    modifier: Modifier = Modifier,
    uiState: PlaceUiState?
) {
    Card(
        modifier = modifier
            .clickable(
                enabled = uiState != null,
                onClickLabel = uiState?.entity?.title,
            ) {
                uiState?.onClick?.invoke()
            }
    ) {
        if (uiState == null) {
            Loading()
        } else {
            UiState(
                uiState = uiState
            )
        }
    }
}

@Composable
private fun Loading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun UiState(
    modifier: Modifier = Modifier,
    uiState: PlaceUiState
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        PlaceHeaderCompose(entity = uiState.entity)
        DiaryMap(
            modifier = Modifier.height(200.dp),
            pin = listOf(uiState.entity),
            isGestureEnable = false,
            isLocationButtonEnable = false,
        )
    }
}