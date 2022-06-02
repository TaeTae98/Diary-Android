package com.taetae98.diary.feature.place.compose

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Launch
import androidx.compose.material.icons.rounded.Map
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.taetae98.diary.feature.compose.diary.DiaryMap
import com.taetae98.diary.feature.place.model.PlaceUiState
import com.taetae98.diary.feature.resource.StringResource

@Composable
fun PlaceCompose(
    modifier: Modifier = Modifier,
    uiState: PlaceUiState?
) {
    Card(
        modifier = modifier
            .clickable(
                onClickLabel = uiState?.entity?.title,
                onClick = uiState?.onClick ?: {}
            )
    ) {
        if (uiState == null) {
            Loading()
        } else {
            UiState(
                modifier = Modifier.padding(8.dp),
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
        TitleLayout(uiState = uiState)
        DiaryMap(
            modifier = Modifier.height(200.dp),
            pin = uiState.entity,
            isGestureEnable = false,
            isLocationButtonEnable = false,
        )
    }
}

@Composable
private fun TitleLayout(
    modifier: Modifier = Modifier,
    uiState: PlaceUiState
) {
    val context = LocalContext.current

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1F),
            text = uiState.entity.title
        )

        IconButton(
            onClick = {
                Intent.createChooser(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("geo:${uiState.entity.latitude},${uiState.entity.longitude}")
                    ),
                    context.getString(StringResource.map)
                ).also {
                    context.startActivity(it)
                }
            }
        ) {
            Icon(imageVector = Icons.Rounded.Map, contentDescription = stringResource(id = StringResource.map))
        }

        uiState.entity.link?.let {
            IconButton(
                onClick = {
                    Intent.createChooser(
                        Intent(Intent.ACTION_VIEW, Uri.parse(it)),
                        context.getString(StringResource.link)
                    ).also {
                        context.startActivity(it)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Launch,
                    contentDescription = stringResource(id = StringResource.link)
                )
            }
        }
    }
}