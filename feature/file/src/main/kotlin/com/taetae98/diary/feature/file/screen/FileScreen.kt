package com.taetae98.diary.feature.file.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.GridItemSpan
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.ContentCut
import androidx.compose.material.icons.rounded.ContentPaste
import androidx.compose.material.icons.rounded.CreateNewFolder
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.taetae98.diary.feature.file.model.FileViewMode
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
        floatingActionButton = { AddButton(navController = navController) },
        bottomBar = { BottomBar(navController = navController) }
    ) {
        Content(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        )
    }

    CollectEvent(snackbarHostState = scaffoldState.snackbarHostState)
}

@Composable
private fun CollectEvent(
    snackbarHostState: SnackbarHostState,
    fileViewModel: FileViewModel = hiltViewModel()
) {
    val (event, setEvent) = remember { mutableStateOf<FileEvent.SecurityAction?>(null) }

    LaunchedEffect(Unit) {
        fileViewModel.event.collect {
            when (it) {
                is FileEvent.Error -> snackbarHostState.showSnackbar("Error : ${it.throwable.message}")
                is FileEvent.SecurityAction -> setEvent(it)
            }
        }
    }

    BackHandler(
        fileViewModel.isBackKeyEnable.collectAsState().value
    ) {
        fileViewModel.back()
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
    val viewMode = fileViewModel.viewMode.collectAsState().value
    val selectedFolder = fileViewModel.selectedFolder.collectAsState().value
    val selectedFile = fileViewModel.selectedFile.collectAsState().value

    val folder = fileViewModel.folder.collectAsState().value
    val folderLazyItems = fileViewModel.folderPaging.collectAsLazyPagingItems()
    val fileLazyItems = fileViewModel.filePaging.collectAsLazyPagingItems()

    LazyVerticalGrid(
        modifier = modifier,
        cells = GridCells.Adaptive(120.dp),
        contentPadding = PaddingValues(8.dp),
    ) {
        if (folder != null && viewMode == FileViewMode.VIEW) {
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
                    uiState = it,
                    isSelectMode = false,
                    isSelected = false
                )
            }
        }

        items(
            count = folderLazyItems.itemCount,
            span = { GridItemSpan(currentLineSpan = Int.MAX_VALUE) }
        ) {
            FolderPreviewCompose(
                uiState = folderLazyItems[it],
                isSelectMode = viewMode != FileViewMode.VIEW,
                isSelected = selectedFolder.contains(folderLazyItems[it]?.entity?.id)
            )
        }

        items(
            count = fileLazyItems.itemCount,
        ) {
            FilePreviewCompose(
                modifier = Modifier.height(160.dp),
                uiState = fileLazyItems[it],
                isSelectMode = viewMode != FileViewMode.VIEW,
                isSelected = selectedFile.contains(fileLazyItems[it]?.entity?.id)
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

@Composable
private fun BottomBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    fileViewModel: FileViewModel = hiltViewModel()
) {
    when (fileViewModel.viewMode.collectAsState().value) {
        FileViewMode.SELECT -> SelectModeBottomBar(
            modifier = modifier,
            navController = navController
        )
        FileViewMode.MOVE -> MoveModeBottomBar(modifier = modifier)
        else -> Unit
    }
}

@Composable
private fun SelectModeBottomBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    fileViewModel: FileViewModel = hiltViewModel()
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RectangleShape,
        elevation = 0.dp
    ) {
        Row {
            val selectedFolder = fileViewModel.selectedFolder.collectAsState().value
            val selectedFile = fileViewModel.selectedFile.collectAsState().value

            BottomBarActionButton(
                modifier = Modifier.weight(1F),
                imageVector = Icons.Rounded.ContentCut,
                contentDescription = stringResource(id = StringResource.cut)
            ) {
                fileViewModel.setViewMode(FileViewMode.MOVE)
            }

            BottomBarActionButton(
                modifier = Modifier.weight(1F),
                imageVector = Icons.Rounded.Delete,
                contentDescription = stringResource(id = StringResource.delete)
            ) {
                fileViewModel.delete()
            }

            BottomBarActionButton(
                modifier = Modifier.weight(1F),
                imageVector = Icons.Rounded.Save,
                contentDescription = stringResource(id = StringResource.delete)
            ) {
                fileViewModel.export()
            }

            if (selectedFile.size + selectedFolder.size == 1) {
                BottomBarActionButton(
                    modifier = Modifier.weight(1F),
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = stringResource(id = StringResource.edit)
                ) {
                    selectedFile.firstOrNull()?.let {
                        navController.navigate(
                            DeepLink.File.getFileDetailAction(id = it)
                        )
                    } ?: selectedFolder.firstOrNull()?.let {
                        navController.navigate(
                            DeepLink.File.getFolderDetailAction(id = it)
                        )
                    }

                    fileViewModel.setViewMode(FileViewMode.VIEW)
                }
            }
        }
    }
}

@Composable
private fun MoveModeBottomBar(
    modifier: Modifier = Modifier,
    fileViewModel: FileViewModel = hiltViewModel()
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RectangleShape,
        elevation = 0.dp
    ) {
        Row {
            BottomBarActionButton(
                modifier = Modifier.weight(1F),
                imageVector = Icons.Rounded.Close,
                contentDescription = stringResource(id = StringResource.cancel)
            ) {
                fileViewModel.setViewMode(FileViewMode.VIEW)
            }

            BottomBarActionButton(
                modifier = Modifier.weight(1F),
                imageVector = Icons.Rounded.ContentPaste,
                contentDescription = stringResource(id = StringResource.paste)
            ) {
                fileViewModel.paste()
            }
        }
    }
}

@Composable
private fun BottomBarActionButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier.padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = onClick
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription
            )
        }
    }
}