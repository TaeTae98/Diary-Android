package com.taetae98.diary.feature.file.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.taetae98.diary.feature.compose.file.FilePreview
import com.taetae98.diary.feature.file.model.FilePreviewUiState

@Composable
fun FilePreviewCompose(
    modifier: Modifier = Modifier,
    uiState: FilePreviewUiState?
) {
    Card(
        modifier = modifier.clickable(
            enabled = uiState != null,
            onClickLabel = uiState?.entity?.title
        ) {
            uiState?.onClick?.invoke()
        },
        shape = RectangleShape,
        elevation = 0.dp
    ) {
        if (uiState == null) {
            Loading()
        } else {
            UiState(uiState = uiState)
        }
    }
}

@Composable
private fun Loading(
    modifier: Modifier = Modifier
) {
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
    uiState: FilePreviewUiState
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        FilePreview(
            modifier = Modifier.size(60.dp),
            path = uiState.entity.path
        )

        Box(
            modifier = Modifier
                .wrapContentHeight()
                .background(Color(0x66000000))
                .align(Alignment.BottomCenter)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                text = uiState.entity.title,
                color = Color.White,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}