package com.taetae98.diary.feature.memo.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taetae98.diary.feature.memo.model.MemoPreviewUiState
import com.taetae98.diary.feature.theme.DiaryTheme

@Composable
fun MemoPreviewCompose(
    modifier: Modifier = Modifier,
    uiState: MemoPreviewUiState? = null
) {
    Card(
        modifier = modifier
            .height(60.dp)
            .clickable(
                enabled = uiState != null,
                onClickLabel = uiState?.title,
            ) {
                uiState?.onClick?.invoke()
            },
    ) {
        if (uiState == null) Loading()
        else UiState(uiState = uiState)
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

@Composable
private fun UiState(
    modifier: Modifier = Modifier,
    uiState: MemoPreviewUiState
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = uiState.title,
            maxLines = 1,
        )
    }
}