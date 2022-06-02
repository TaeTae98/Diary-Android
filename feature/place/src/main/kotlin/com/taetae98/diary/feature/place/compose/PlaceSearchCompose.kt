package com.taetae98.diary.feature.place.compose

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Launch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.taetae98.diary.feature.place.model.PlaceSearchUiState
import com.taetae98.diary.feature.resource.StringResource
import com.taetae98.diary.feature.theme.DiaryTheme

@Composable
fun PlaceSearchCompose(
    modifier: Modifier = Modifier,
    uiState: PlaceSearchUiState?,
) {
    Card(
        modifier = modifier
            .clickable {
                uiState?.onClick?.invoke()
            },
    ) {
        if (uiState == null) Loading()
        else UiState(uiState = uiState)
    }
}

@Composable
private fun Loading(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier,
        color = DiaryTheme.primaryColor
    )
}

@Composable
private fun UiState(
    modifier: Modifier = Modifier,
    uiState: PlaceSearchUiState
) {
    Column(
        modifier = modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        TitleLayout(uiState = uiState)
        Text(text = uiState.entity.address)
        Divider()
        Text(text = uiState.entity.description)
    }
}

@Composable
private fun TitleLayout(
    modifier: Modifier = Modifier,
    uiState: PlaceSearchUiState
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1F),
            text = uiState.entity.title
        )
        uiState.entity.link?.let { LinkButton(link = it) }
    }
}

@Composable
private fun LinkButton(
    modifier: Modifier = Modifier,
    link: String
) {
    val context = LocalContext.current
    IconButton(
        modifier = modifier,
        onClick = {
            Intent.createChooser(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(link)
                ),
                context.getString(StringResource.link)
            ).also {
                context.startActivity(it)
            }
        }
    ) {
        Icon(
            imageVector = Icons.Rounded.Launch, contentDescription = stringResource(
                id = StringResource.search
            )
        )
    }
}