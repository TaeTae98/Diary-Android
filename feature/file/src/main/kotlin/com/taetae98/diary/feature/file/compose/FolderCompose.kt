package com.taetae98.diary.feature.file.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.taetae98.diary.feature.resource.StringResource

@Composable
fun FolderCompose(
    modifier: Modifier = Modifier,
    text: String,
) {
    Row(
        modifier = modifier.heightIn(min = 48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Rounded.Folder,
            contentDescription = stringResource(id = StringResource.folder)
        )

        Text(
            modifier = modifier.padding(start = 8.dp),
            text = text
        )
    }
}