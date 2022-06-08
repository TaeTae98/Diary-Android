package com.taetae98.diary.feature.file

import android.media.MediaMetadataRetriever
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.CreateNewFolder
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.VideoFrameDecoder
import com.taetae98.diary.domain.model.file.FileEntity
import com.taetae98.diary.feature.compose.diary.DiaryTopAppBar
import com.taetae98.diary.feature.compose.input.ClearTextField
import com.taetae98.diary.feature.resource.StringResource

@Composable
fun FileScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    fileViewModel: FileViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = { FileTopAppBar() },
        floatingActionButton = { AddButton() }
    ) {
        val lazyPagingItems = fileViewModel.pagingData.collectAsLazyPagingItems()
        LazyColumn(
            modifier = Modifier.padding(it)
        ) {
            items(
                items = lazyPagingItems,
                key = { item -> item.id }
            ) { item ->
                Card {
                    if (item == null || item.state == FileEntity.State.WRITING) {
                        CircularProgressIndicator()
                    } else {
                        Column {
                            Text(text = item.title)
                            Text(text = item.path)
                            Text(text = "${item.isImage() || item.isApk() || item.isVideo()}")
                            if (item.isImage()) {
                                AsyncImage(
                                    model = item.path,
                                    contentDescription = item.title,
                                    imageLoader = ImageLoader.Builder(LocalContext.current)
                                        .components {
                                            add(GifDecoder.Factory())
                                        }
                                        .build()
                                )
                            } else if (item.isApk()) {
                                val context = LocalContext.current
                                val packageManager = context.packageManager
                                val packageInfo = packageManager.getPackageArchiveInfo(item.path, 0)
                                val drawable =
                                    packageInfo?.applicationInfo?.loadIcon(packageManager)

                                AsyncImage(model = drawable, contentDescription = "")
                            } else if (item.isVideo()) {
                                AsyncImage(
                                    model = item.path,
                                    contentDescription = item.title,
                                    imageLoader = ImageLoader.Builder(LocalContext.current)
                                        .components {
                                            add(VideoFrameDecoder.Factory())
                                        }.build()
                                )
                            } else if (item.isAudio()) {
                                AsyncImage(
                                    model = MediaMetadataRetriever().apply {
                                        setDataSource(item.path)
                                    }.embeddedPicture,
                                    contentDescription = item.title,
                                )
                            }

                        }
                    }
                }
            }
        }
    }

    CollectEvent(snackbarHostState = scaffoldState.snackbarHostState)
}

@Composable
private fun CollectEvent(
    snackbarHostState: SnackbarHostState,
    fileViewModel: FileViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        fileViewModel.event.collect {
            when (it) {
                FileEvent.Success -> snackbarHostState.showSnackbar("Success")
                FileEvent.Fail -> snackbarHostState.showSnackbar("Fail")
            }
        }
    }
}

@Composable
private fun FileTopAppBar(
    modifier: Modifier = Modifier,
    fileViewModel: FileViewModel = hiltViewModel()
) {
    val (isFolderDialogVisible, setFolderDialogVisible) = remember { mutableStateOf(false) }
    DiaryTopAppBar(
        modifier = modifier,
        title = { Text(text = stringResource(id = StringResource.file)) },
        actions = {
            IconButton(
                onClick = {
                    setFolderDialogVisible(true)
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.CreateNewFolder,
                    contentDescription = stringResource(
                        id = R.string.new_folder
                    )
                )
            }
        }
    )

    Dialog(
        onDismissRequest = {
            setFolderDialogVisible(false)
        }
    ) {
        val (title, setTitle) = remember { mutableStateOf("") }
        Column {
            ClearTextField(value = title, onValueChange = setTitle)
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 48.dp)
                    .padding(0.dp),
                onClick = { /*TODO*/ }
            ) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = stringResource(id = StringResource.add))
            }
        }
    }
}

@Composable
private fun AddButton(
    modifier: Modifier = Modifier,
    fileViewModel: FileViewModel = hiltViewModel()
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        it.let { fileViewModel.save(it) }
    }

    FloatingActionButton(
        modifier = modifier,
        onClick = { launcher.launch("*/*") }
    ) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = stringResource(id = StringResource.add)
        )
    }
}