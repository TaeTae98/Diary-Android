package com.taetae98.diary.feature.file.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.taetae98.diary.feature.file.model.FolderPreviewUiState
import com.taetae98.diary.feature.resource.StringResource

@Composable
fun FolderPreviewCompose(
    modifier: Modifier = Modifier,
    uiState: FolderPreviewUiState?
) {
    Card(
        modifier = modifier.height(50.dp).clickable(
            enabled = uiState != null,
            onClickLabel = uiState?.entity?.title,
        ) {
            uiState?.onClick?.invoke()
        },
        shape = RectangleShape,
        elevation = 0.dp
    ) {
        if (uiState == null) {
            CircularProgressIndicator()
        } else {
            UiState(uiState = uiState)
        }
    }
}

@Composable
private fun UiState(
    modifier: Modifier = Modifier,
    uiState: FolderPreviewUiState
) {
    Row(
        modifier = modifier.padding(horizontal = 8.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Rounded.Folder,
            contentDescription = stringResource(id = StringResource.folder)
        )

        Text(
            text = uiState.entity.title
        )
    }
}