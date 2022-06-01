package com.taetae98.diary.feature.location.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taetae98.diary.feature.location.model.LocationSearchQueryUiState
import com.taetae98.diary.feature.resource.StringResource
import com.taetae98.diary.feature.theme.DiaryTheme

@Composable
fun LocationSearchQueryCompose(
    modifier: Modifier = Modifier,
    uiState: LocationSearchQueryUiState?
) {
    Card(
        modifier = modifier,
        shape = RectangleShape,
    ) {
        if (uiState == null) {
            Loading()
        } else {
            UiState(uiState = uiState)
        }
    }
}

@Composable
private fun UiState(
    modifier: Modifier = Modifier,
    uiState: LocationSearchQueryUiState
) {
    Row(
        modifier = modifier.padding(start = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1F)
        ) {
            Text(
                text = uiState.searchedAt,
                fontSize = 12.sp
            )
            Text(
                text = uiState.query
            )
        }

        IconButton(
            onClick = uiState.onDelete
        ) {
            Icon(
                imageVector = Icons.Rounded.Clear,
                contentDescription = stringResource(id = StringResource.remove)
            )
        }
    }
}

@Composable
private fun Loading(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
        modifier = modifier.wrapContentSize(),
        color = DiaryTheme.onSurfaceColor,
    )
}