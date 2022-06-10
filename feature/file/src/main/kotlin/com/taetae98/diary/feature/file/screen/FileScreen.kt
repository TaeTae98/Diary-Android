package com.taetae98.diary.feature.file.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.GridItemSpan
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.CreateNewFolder
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.taetae98.diary.domain.model.file.FolderEntity
import com.taetae98.diary.feature.common.DeepLink
import com.taetae98.diary.feature.compose.diary.DiaryTopAppBar
import com.taetae98.diary.feature.compose.input.PasswordDialog
import com.taetae98.diary.feature.file.R
import com.taetae98.diary.feature.file.compose.FilePreviewCompose
import com.taetae98.diary.feature.file.compose.FolderPreviewCompose
import com.taetae98.diary.feature.file.event.FileEvent
import com.taetae98.diary.feature.file.model.FolderPreviewUiState
import com.taetae98.diary.feature.file.viewmodel.FileViewModel
import com.taetae98.diary.feature.resource.StringResource

@Composable
fun FileScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = { FileTopAppBar(navController = navController) },
        floatingActionButton = { AddButton(navController = navController) }
    ) {
        Content(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        )
    }

    CollectEvent(
        snackbarHostState = scaffoldState.snackbarHostState,
        navController = navController
    )
}

@Composable
private fun CollectEvent(
    snackbarHostState: SnackbarHostState,
    navController: NavController,
    fileViewModel: FileViewModel = hiltViewModel()
) {
    val (event, setEvent) = remember { mutableStateOf<FileEvent.SecurityAction?>(null) }

    LaunchedEffect(Unit) {
        fileViewModel.event.collect {
            when (it) {
                is FileEvent.Error -> snackbarHostState.showSnackbar("Error : ${it.throwable.message}")
                is FileEvent.SecurityAction -> setEvent(it)
                is FileEvent.OnClickFile -> navController.navigate(
                    DeepLink.File.getFileDetailAction(
                        id = it.entity.id
                    )
                )
            }
        }
    }

    BackHandler(
        fileViewModel.folder.collectAsState().value != null
    ) {
        fileViewModel.navigate(fileViewModel.folder.value?.parentId)
    }

    if (event != null) {
        PasswordDialog(
            password = event.password,
            onSuccess = {
                setEvent(null)
                event.onAction()
            },
            onDismissRequest = { setEvent(null) }
        )
    }
}

@Composable
private fun FileTopAppBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    fileViewModel: FileViewModel = hiltViewModel()
) {
    DiaryTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = fileViewModel.folder.collectAsState().value?.title ?: stringResource(
                    id = StringResource.file
                )
            )
        },
        actions = {
            val folder = fileViewModel.folder.collectAsState().value
            if (folder != null) {
                IconButton(
                    onClick = fileViewModel::deleteFolder
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = stringResource(id = StringResource.edit)
                    )
                }

                IconButton(
                    onClick = {
                        navController.navigate(
                            DeepLink.File.getFolderDetailAction(id = folder.id)
                        )
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = stringResource(id = StringResource.edit)
                    )
                }
            }
            IconButton(
                onClick = {
                    navController.navigate(
                        DeepLink.File.getFolderDetailAction(parentId = fileViewModel.folder.value?.id)
                    )
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
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Content(
    modifier: Modifier = Modifier,
    fileViewModel: FileViewModel = hiltViewModel()
) {
    val folder = fileViewModel.folder.collectAsState().value
    val folderLazyItems = fileViewModel.folderPaging.collectAsLazyPagingItems()
    val fileLazyItems = fileViewModel.filePaging.collectAsLazyPagingItems()

    LazyVerticalGrid(
        modifier = modifier,
        cells = GridCells.Adaptive(120.dp),
        contentPadding = PaddingValues(8.dp),
    ) {
        if (folder != null) {
            items(
                items = listOf(
                    FolderPreviewUiState(
                        entity = FolderEntity(title = "..."),
                        onClick = {
                            fileViewModel.navigate(
                                fileViewModel.folder.value?.parentId
                            )
                        }
                    )
                ),
                spans = { GridItemSpan(currentLineSpan = Int.MAX_VALUE) }
            ) {
                FolderPreviewCompose(
                    uiState = it
                )
            }
        }

        items(
            count = folderLazyItems.itemCount,
            span = { GridItemSpan(currentLineSpan = Int.MAX_VALUE) }
        ) {
            FolderPreviewCompose(
                uiState = folderLazyItems[it]
            )
        }

        items(
            count = fileLazyItems.itemCount,
        ) {
            FilePreviewCompose(
                modifier = Modifier.height(160.dp),
                uiState = fileLazyItems[it]
            )
        }
    }
}

@Composable
private fun AddButton(
    modifier: Modifier = Modifier,
    navController: NavController,
    fileViewModel: FileViewModel = hiltViewModel()
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = {
            navController.navigate(
                DeepLink.File.getFileDetailAction(
                    parentId = fileViewModel.folder.value?.id
                )
            )
        }
    ) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = stringResource(id = StringResource.add)
        )
    }
}