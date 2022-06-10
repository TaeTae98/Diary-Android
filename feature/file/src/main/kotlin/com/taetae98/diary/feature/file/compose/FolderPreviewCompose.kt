package com.taetae98.diary.feature.file.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckBox
import androidx.compose.material.icons.rounded.CheckBoxOutlineBlank
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.taetae98.diary.feature.file.model.FolderPreviewUiState
import com.taetae98.diary.feature.resource.StringResource
import com.taetae98.diary.feature.theme.DiaryTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FolderPreviewCompose(
    modifier: Modifier = Modifier,
    uiState: FolderPreviewUiState?,
    isSelectMode: Boolean,
    isSelected: Boolean,
) {
    Card(
        modifier = modifier
            .height(50.dp)
            .combinedClickable(
                enabled = uiState != null,
                onClickLabel = uiState?.entity?.title,
                onLongClickLabel = uiState?.entity?.title,
                onClick = uiState?.onClick ?: {},
                onLongClick = uiState?.onLongClick
            ),
        shape = RectangleShape,
        elevation = 0.dp
    ) {
        if (uiState == null) {
            CircularProgressIndicator()
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
private fun UiState(
    modifier: Modifier = Modifier,
    uiState: FolderPreviewUiState,
    isSelectMode: Boolean,
    isSelected: Boolean,
) {
    Row(
        modifier = modifier.padding(horizontal = 8.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isSelectMode) {
            if (isSelected) {
                Icon(
                    modifier = Modifier.clickable(
                        enabled = uiState.onSelect != null
                    ) {
                        uiState.onSelect?.invoke()
                    },
                    tint = DiaryTheme.primaryColor,
                    imageVector = Icons.Rounded.CheckBox,
                    contentDescription = stringResource(id = StringResource.checked)
                )
            } else {
                Icon(
                    modifier = Modifier.clickable(
                        enabled = uiState.onSelect != null
                    ) {
                        uiState.onSelect?.invoke()
                    },
                    tint = DiaryTheme.primaryColor,
                    imageVector = Icons.Rounded.CheckBoxOutlineBlank,
                    contentDescription = stringResource(id = StringResource.not_checked)
                )
            }
        }
        Icon(
            imageVector = Icons.Rounded.Folder,
            contentDescription = stringResource(id = StringResource.folder)
        )

        Text(
            text = uiState.entity.title
        )
    }
}