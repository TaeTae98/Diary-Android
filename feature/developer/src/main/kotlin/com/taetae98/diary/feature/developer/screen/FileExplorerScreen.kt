package com.taetae98.diary.feature.developer.screen

import android.text.format.Formatter
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.taetae98.diary.feature.compose.diary.DiaryTopAppBar
import com.taetae98.diary.feature.compose.diary.DiaryTopAppBarNavigationIcon
import com.taetae98.diary.feature.compose.file.FilePreview
import com.taetae98.diary.feature.developer.event.FileExplorerEvent
import com.taetae98.diary.feature.developer.model.FileExplorerUiState
import com.taetae98.diary.feature.developer.viewmodel.FileExplorerViewModel
import com.taetae98.diary.feature.resource.StringResource
import com.taetae98.diary.feature.theme.DiaryTheme

@Composable
fun FileExplorerScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = { FileExplorerTopAppBar(navController = navController) }
    ) {
        Content(modifier = Modifier.padding(it))
    }

    CollectEvent(snackbarHostState = scaffoldState.snackbarHostState)
}

@Composable
private fun CollectEvent(
    snackbarHostState: SnackbarHostState,
    fileExplorerViewModel: FileExplorerViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        fileExplorerViewModel.event.collect {
            when (it) {
                is FileExplorerEvent.Error -> snackbarHostState.showSnackbar(
                    "Error : ${it.throwable.message}"
                )
            }
        }
    }
}

@Composable
private fun FileExplorerTopAppBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    fileExplorerViewModel: FileExplorerViewModel = hiltViewModel()
) {
    DiaryTopAppBar(
        modifier = modifier,
        navigationIcon = { DiaryTopAppBarNavigationIcon(navController = navController) },
        actions = {
            IconButton(
                onClick = {
                    fileExplorerViewModel.fix()
                }
            ) {
                Icon(imageVector = Icons.Rounded.Build, contentDescription = stringResource(id = StringResource.fix))
            }
        }
    )
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    fileExplorerViewModel: FileExplorerViewModel = hiltViewModel()
) {
    val items = fileExplorerViewModel.file.collectAsState().value
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp)
    ) {
        items(
            items = items,
            key = { it.file.path }
        ) {
            File(
                modifier = Modifier.fillParentMaxWidth(),
                uiState = it
            )
        }
    }
}

@Composable
private fun File(
    modifier: Modifier = Modifier,
    uiState: FileExplorerUiState
) {
    Card(
        modifier = modifier,
        shape = RectangleShape
    ) {
        Row(
            modifier = modifier
        ) {
            Thumbnail(uiState = uiState)
            Column {
                Text(
                    text = uiState.file.name,
                    color = if (uiState.isWarning) DiaryTheme.errorColor else Color.Unspecified
                )
                Text(text = Formatter.formatFileSize(LocalContext.current, uiState.file.length()))
            }
        }
    }
}

@Composable
private fun Thumbnail(
    modifier: Modifier = Modifier,
    uiState: FileExplorerUiState
) {
    Box(modifier = modifier.size(100.dp)) {
        FilePreview(
            path = uiState.file.path,
        )
    }
}
