package com.taetae98.diary.feature.compose.place

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
import com.taetae98.diary.domain.model.place.PlaceEntity
import com.taetae98.diary.feature.resource.StringResource

@Composable
fun PlaceHeaderCompose(
    modifier: Modifier = Modifier,
    entity: PlaceEntity
) {
    val context = LocalContext.current

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1F).padding(start = 8.dp),
            text = entity.title
        )

        IconButton(
            onClick = {
                Intent.createChooser(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("geo:${entity.latitude},${entity.longitude}")
                    ),
                    context.getString(StringResource.map)
                ).also {
                    context.startActivity(it)
                }
            }
        ) {
            Icon(imageVector = Icons.Rounded.Map, contentDescription = stringResource(id = StringResource.map))
        }

        entity.link?.let {
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