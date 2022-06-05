package com.taetae98.diary.feature.developer.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.taetae98.diary.domain.exception.KnownIssueException
import com.taetae98.diary.feature.compose.diary.DiaryTopAppBar
import com.taetae98.diary.feature.compose.diary.DiaryTopAppBarNavigationIcon
import com.taetae98.diary.feature.compose.modifier.swipeable
import com.taetae98.diary.feature.developer.R
import com.taetae98.diary.feature.developer.event.ExceptionLogEvent
import com.taetae98.diary.feature.developer.model.ExceptionLogUiState
import com.taetae98.diary.feature.developer.viewmodel.ExceptionLogViewModel
import com.taetae98.diary.feature.resource.StringResource
import com.taetae98.diary.feature.theme.DiaryTheme
import kotlinx.coroutines.flow.collect

object ExceptionLogScreen {
    const val ROUTE = "ExceptionLogScreen"

    fun getAction(): String {
        return ROUTE
    }
}

@Composable
fun ExceptionLogScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = { ExceptionLogTopAppBar(navController = navController) }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            ActionCompose()
            ExceptionLogColumn()
        }
    }

    CollectEvent(snackbarHostState = scaffoldState.snackbarHostState)
}

@Composable
private fun CollectEvent(
    snackbarHostState: SnackbarHostState,
    exceptionLogViewModel: ExceptionLogViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        exceptionLogViewModel.event.collect {
            when (it) {
                is ExceptionLogEvent.DeleteExceptionLog -> {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(
                        message = it.collection.firstOrNull()?.entity?.cause ?: "",
                        actionLabel = context.getString(StringResource.restore),
                    ).also { result ->
                        if (result == SnackbarResult.ActionPerformed) {
                            it.onRestore()
                        }
                    }
                }
                is ExceptionLogEvent.Error -> {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(
                        message = "Error : ${it.throwable.message}"
                    )
                }
            }
        }
    }
}

@Composable
private fun ExceptionLogTopAppBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    exceptionLogViewModel: ExceptionLogViewModel = hiltViewModel()
) {
    DiaryTopAppBar(
        modifier = modifier,
        title = { Text(text = stringResource(id = R.string.exception_log)) },
        navigationIcon = {
            DiaryTopAppBarNavigationIcon(navController = navController)
        },
        actions = {
            IconButton(
                onClick = { exceptionLogViewModel.deleteAll() }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = stringResource(id = StringResource.clear)
                )
            }
        }
    )
}

@Composable
private fun ActionCompose(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier
                .clickable { throw KnownIssueException() }
                .widthIn(min = 48.dp)
                .heightIn(48.dp)
        ) {
            Box(
                modifier = Modifier.padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Throw")
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ExceptionLogColumn(
    modifier: Modifier = Modifier,
    exceptionLogViewModel: ExceptionLogViewModel = hiltViewModel()
) {
    val lazyPagingItems = exceptionLogViewModel.paging.collectAsLazyPagingItems()
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(
            items = lazyPagingItems,
            key = { it.id }
        ) {
            ExceptionLogCompose(
                modifier = Modifier
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

@Composable
private fun ExceptionLogCompose(
    modifier: Modifier = Modifier,
    uiState: ExceptionLogUiState?
) {
    Card(
        modifier = modifier
    ) {
        if (uiState == null) {
            Loading()
        } else {
            Column {
                Text(text = uiState.cause)
                Text(text = uiState.type)
                Text(text = uiState.createdAt)
                Divider()
                Text(text = uiState.stackTrace)
            }
        }
    }
}

@Composable
private fun Loading(
    modifier: Modifier = Modifier,
) {
    CircularProgressIndicator(
        modifier = modifier.wrapContentSize(),
        color = DiaryTheme.onSurfaceColor,
    )
}