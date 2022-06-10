package com.taetae98.diary.feature.file.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckBox
import androidx.compose.material.icons.rounded.CheckBoxOutlineBlank
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.taetae98.diary.feature.compose.file.FilePreview
import com.taetae98.diary.feature.file.model.FilePreviewUiState
import com.taetae98.diary.feature.resource.StringResource
import com.taetae98.diary.feature.theme.DiaryTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FilePreviewCompose(
    modifier: Modifier = Modifier,
    uiState: FilePreviewUiState?,
    isSelectMode: Boolean,
    isSelected: Boolean,
) {
    Card(
        modifier = modifier.combinedClickable(
            enabled = uiState != null,
            onClickLabel = uiState?.entity?.title,
            onLongClickLabel = uiState?.entity?.title,
            onClick = uiState?.onClick ?: {},
            onLongClick = uiState?.onLongClick,
        ),
        shape = RectangleShape,
        elevation = 0.dp
    ) {
        if (uiState == null) {
            Loading()
        } else {
            UiState(
                uiState = uiState,
                isSelectMode = isSelectMode,
                isSelected = isSelected
            )
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
    uiState: FilePreviewUiState,
    isSelectMode: Boolean,
    isSelected: Boolean,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        FilePreview(
            modifier = Modifier.size(60.dp),
            path = uiState.entity.path
        )

        Text(
            modifier = Modifier
                .wrapContentHeight()
                .background(Color(0x66000000))
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(2.dp),
            text = uiState.entity.title,
            color = Color.White,
            textAlign = TextAlign.Center,
            maxLines = 1
        )

        if (isSelectMode) {
            if (isSelected) {
                Icon(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.TopStart),
                    tint = DiaryTheme.primaryColor,
                    imageVector = Icons.Rounded.CheckBox,
                    contentDescription = stringResource(id = StringResource.checked)
                )
            } else {
                Icon(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.TopStart),
                    tint = DiaryTheme.primaryColor,
                    imageVector = Icons.Rounded.CheckBoxOutlineBlank,
                    contentDescription = stringResource(id = StringResource.not_checked)
                )
            }
        }
    }
}