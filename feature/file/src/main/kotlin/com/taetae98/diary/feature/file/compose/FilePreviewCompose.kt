package com.taetae98.diary.feature.file.compose

import android.media.MediaMetadataRetriever
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Android
import androidx.compose.material.icons.rounded.InsertDriveFile
import androidx.compose.material.icons.rounded.MusicNote
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.VideoFrameDecoder
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
        when {
            uiState.entity.isVideo() -> VideoPreview(uiState = uiState)
            uiState.entity.isImage() -> ImagePreview(uiState = uiState)
            uiState.entity.isAudio() -> AudioPreview(uiState = uiState)
            uiState.entity.isApk() -> ApkPreview(uiState = uiState)
            else -> Preview(uiState = uiState)
        }

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

@Composable
private fun ImagePreview(
    modifier: Modifier = Modifier,
    uiState: FilePreviewUiState
) {
    AsyncImage(
        modifier = modifier.fillMaxSize(),
        model = uiState.entity.path,
        contentDescription = uiState.entity.title,
        imageLoader = ImageLoader.Builder(LocalContext.current)
            .components {
                add(GifDecoder.Factory())
            }
            .build(),
        placeholder = rememberVectorPainter(image = Icons.Rounded.InsertDriveFile),
        error = rememberVectorPainter(image = Icons.Rounded.InsertDriveFile),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun ApkPreview(
    modifier: Modifier = Modifier,
    uiState: FilePreviewUiState
) {
    with(LocalContext.current) {
        packageManager.getPackageArchiveInfo(uiState.entity.path, 0)?.let {
            it.applicationInfo?.loadIcon(packageManager)
        }?.let {
            AsyncImage(
                modifier = modifier.size(80.dp),
                model = it,
                contentDescription = uiState.entity.title,
                placeholder = rememberVectorPainter(image = Icons.Rounded.InsertDriveFile),
                error = rememberVectorPainter(image = Icons.Rounded.InsertDriveFile),
                contentScale = ContentScale.Crop
            )
        } ?: Preview(
            uiState = uiState,
            imageVector = Icons.Rounded.Android
        )
    }
}

@Composable
private fun VideoPreview(
    modifier: Modifier = Modifier,
    uiState: FilePreviewUiState
) {
    AsyncImage(
        modifier = modifier,
        model = uiState.entity.path,
        contentDescription = uiState.entity.title,
        imageLoader = ImageLoader.Builder(LocalContext.current)
            .components {
                add(VideoFrameDecoder.Factory())
            }.build(),
        placeholder = rememberVectorPainter(image = Icons.Rounded.InsertDriveFile),
        error = rememberVectorPainter(image = Icons.Rounded.InsertDriveFile),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun AudioPreview(
    modifier: Modifier = Modifier,
    uiState: FilePreviewUiState
) {
    MediaMetadataRetriever().apply {
        setDataSource(uiState.entity.path)
    }.embeddedPicture?.let {
        AsyncImage(
            modifier = modifier,
            model = it,
            contentDescription = uiState.entity.title,
            placeholder = rememberVectorPainter(image = Icons.Rounded.InsertDriveFile),
            error = rememberVectorPainter(image = Icons.Rounded.InsertDriveFile),
            contentScale = ContentScale.Crop
        )
    } ?: Preview(
        uiState = uiState,
        imageVector = Icons.Rounded.MusicNote
    )
}

@Composable
private fun Preview(
    modifier: Modifier = Modifier,
    uiState: FilePreviewUiState,
    imageVector: ImageVector = Icons.Rounded.InsertDriveFile
) {
    Image(
        modifier = modifier.size(80.dp),
        imageVector = imageVector,
        contentDescription = uiState.entity.title
    )
}