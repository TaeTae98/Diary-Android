package com.taetae98.diary.feature.memo.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.taetae98.diary.feature.common.DeepLink
import com.taetae98.diary.feature.compose.diary.DiaryTopAppBar
import com.taetae98.diary.feature.compose.input.PasswordDialog
import com.taetae98.diary.feature.compose.modifier.swipeable
import com.taetae98.diary.feature.memo.compose.MemoPreviewCompose
import com.taetae98.diary.feature.memo.event.MemoEvent
import com.taetae98.diary.feature.memo.viewmodel.MemoViewModel
import com.taetae98.diary.feature.resource.StringResource
import com.taetae98.diary.feature.theme.DiaryTheme
import kotlinx.coroutines.flow.collect

@Composable
fun MemoScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = { MemoTopAppBar() },
        floatingActionButton = { AddButton(navController = navController) }
    ) {
        MemoLazyColumn(
            modifier = Modifier.padding(it)
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
    memoViewModel: MemoViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val (event, setEvent) = remember { mutableStateOf<MemoEvent.SecurityAction?>(null) }

    LaunchedEffect(Unit) {
        memoViewModel.event.collect {
            when (it) {
                is MemoEvent.Delete -> {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(
                        message = it.relation.memo.title,
                        actionLabel = context.getString(StringResource.restore),
                        duration = SnackbarDuration.Short
                    ).also { result ->
                        if (result == SnackbarResult.ActionPerformed) {
                            it.onRestore()
                        }
                    }
                }
                is MemoEvent.Error -> {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(
                        message = "Error : ${it.throwable.message}"
                    )
                }
                is MemoEvent.Detail -> {
                    navController.navigate(DeepLink.Memo.getMemoDetailAction(id = it.id))
                }
                is MemoEvent.SecurityAction -> {
                    setEvent(it)
                }
            }
        }
    }

    if (event != null) {
        PasswordDialog(
            password = event.password,
            onSuccess = {
                event.onAction()
                setEvent(null)
            },
            onDismissRequest = { setEvent(null) }
        )
    }
}

@Composable
private fun MemoTopAppBar(
    modifier: Modifier = Modifier,
) {
    DiaryTopAppBar(
        modifier = modifier,
        title = { Text(text = stringResource(id = StringResource.memo)) },
    )
}

@Composable
private fun AddButton(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = {
            navController.navigate(DeepLink.Memo.getMemoDetailAction())
        }
    ) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = stringResource(id = StringResource.add)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MemoLazyColumn(
    modifier: Modifier = Modifier,
    memoViewModel: MemoViewModel = hiltViewModel()
) {
    val items = memoViewModel.getMemoByTagIds(emptyList()).collectAsLazyPagingItems()

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(
            items = items,
            key = { it.id }
        ) {
            MemoPreviewCompose(
                modifier = modifier
                    .fillParentMaxWidth()
                    .swipeable(
                        enabled = it != null
                    ) { _, targetValue ->
                        if (targetValue != 0) it?.onDelete?.invoke()
                    },
                uiState = it
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    DiaryTheme {
        MemoScreen(
            navController = rememberNavController()
        )
    }
}