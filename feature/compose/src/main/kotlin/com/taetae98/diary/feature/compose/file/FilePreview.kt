package com.taetae98.diary.feature.compose.file

import android.media.MediaMetadataRetriever
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Android
import androidx.compose.material.icons.rounded.Audiotrack
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.icons.rounded.InsertDriveFile
import androidx.compose.material.icons.rounded.Videocam
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.VideoFrameDecoder
import com.taetae98.diary.feature.common.util.isApk
import com.taetae98.diary.feature.common.util.isAudio
import com.taetae98.diary.feature.common.util.isImage
import com.taetae98.diary.feature.common.util.isVideo

@Composable
fun FilePreview(
    modifier: Modifier = Modifier,
    path: String,
    contentDescription: String? = null
) {
    when {
        path.isImage() -> ImageThumbnail(
            modifier = modifier,
            path = path,
            contentDescription = contentDescription
        )

        path.isAudio() -> AudioThumbnail(
            modifier = modifier,
            path = path,
            contentDescription = contentDescription
        )

        path.isVideo() -> VideoThumbnail(
            modifier = modifier,
            path = path,
            contentDescription = contentDescription
        )

        path.isApk() -> ApkThumbnail(
            modifier = modifier,
            path = path,
            contentDescription = contentDescription
        )

        else -> Preview(
            modifier = modifier,
            contentDescription = contentDescription
        )
    }
}

@Composable
private fun ImageThumbnail(
    modifier: Modifier = Modifier,
    path: String,
    contentDescription: String? = null
) {
    val (isSuccess, setSuccess) = remember { mutableStateOf(false) }

    AsyncImage(
        modifier = if (isSuccess) Modifier.fillMaxSize() else modifier,
        model = path,
        contentDescription = contentDescription,
        imageLoader = ImageLoader.Builder(LocalContext.current)
            .components {
                add(GifDecoder.Factory())
            }
            .build(),
        placeholder = rememberVectorPainter(image = Icons.Rounded.Image),
        error = rememberVectorPainter(image = Icons.Rounded.Image),
        onSuccess = { setSuccess(true) },
        contentScale = ContentScale.Crop,
    )
}

@Composable
private fun AudioThumbnail(
    modifier: Modifier = Modifier,
    path: String,
    contentDescription: String? = null,
) {
    val (isSuccess, setSuccess) = remember { mutableStateOf(false) }

    AsyncImage(
        modifier = if (isSuccess) Modifier.fillMaxSize() else modifier,
        model = MediaMetadataRetriever().apply {
            setDataSource(path)
        }.embeddedPicture,
        contentDescription = contentDescription,
        placeholder = rememberVectorPainter(image = Icons.Rounded.Audiotrack),
        error = rememberVectorPainter(image = Icons.Rounded.Audiotrack),
        onSuccess = { setSuccess(true) },
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun VideoThumbnail(
    modifier: Modifier = Modifier,
    path: String,
    contentDescription: String? = null
) {
    val (isSuccess, setSuccess) = remember { mutableStateOf(false) }

    AsyncImage(
        modifier = if (isSuccess) Modifier.fillMaxSize() else modifier,
        model = path,
        contentDescription = contentDescription,
        imageLoader = ImageLoader.Builder(LocalContext.current)
            .components {
                add(VideoFrameDecoder.Factory())
            }.build(),
        placeholder = rememberVectorPainter(image = Icons.Rounded.Videocam),
        error = rememberVectorPainter(image = Icons.Rounded.Videocam),
        onSuccess = { setSuccess(true) },
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun ApkThumbnail(
    modifier: Modifier = Modifier,
    path: String,
    contentDescription: String? = null,
) {
    AsyncImage(
        modifier = modifier,
        model = LocalContext.current.packageManager
            .getPackageArchiveInfo(
                path,
                0
            )?.applicationInfo?.loadIcon(LocalContext.current.packageManager),
        contentDescription = contentDescription,
        placeholder = rememberVectorPainter(image = Icons.Rounded.Android),
        error = rememberVectorPainter(image = Icons.Rounded.Android),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun Preview(
    modifier: Modifier = Modifier,
    contentDescription: String? = null
) {
    Image(
        modifier = modifier,
        imageVector = Icons.Rounded.InsertDriveFile,
        contentDescription = contentDescription
    )
}