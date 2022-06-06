package com.taetae98.diary.feature.place.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.taetae98.diary.feature.common.DeepLink
import com.taetae98.diary.feature.compose.diary.DiaryTopAppBar
import com.taetae98.diary.feature.compose.input.PasswordDialog
import com.taetae98.diary.feature.compose.modifier.swipeable
import com.taetae98.diary.feature.place.compose.PlacePreviewCompose
import com.taetae98.diary.feature.place.event.PlaceEvent
import com.taetae98.diary.feature.place.viewmodel.PlaceViewModel
import com.taetae98.diary.feature.resource.StringResource
import kotlinx.coroutines.flow.collect

@Composable
fun PlaceScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = { PlaceTopAppBar() },
        floatingActionButton = { AddButton(navController = navController) }
    ) {
        Content(
            modifier = Modifier.padding(it),
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
    placeViewModel: PlaceViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val (event, setEvent) = remember { mutableStateOf<PlaceEvent.SecurityAction?>(null) }

    LaunchedEffect(Unit) {
        placeViewModel.event.collect {
            when (it) {
                is PlaceEvent.Detail -> {
                    navController.navigate(DeepLink.Place.getPlaceDetailAction(it.entity.id))
                }
                is PlaceEvent.Delete -> {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(
                        message = it.relation.place.title,
                        actionLabel = context.getString(StringResource.restore),
                    ).also { result ->
                        if (result == SnackbarResult.ActionPerformed) {
                            it.onRestore()
                        }
                    }
                }
                is PlaceEvent.SecurityAction -> {
                    setEvent(it)
                }
                is PlaceEvent.Error -> {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(
                        message = "Error ${it.throwable.message}"
                    )
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
private fun PlaceTopAppBar(
    modifier: Modifier = Modifier,
) {
    DiaryTopAppBar(
        modifier = modifier,
        title = { Text(text = stringResource(id = StringResource.place)) },
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Content(
    modifier: Modifier = Modifier,
    placeViewModel: PlaceViewModel = hiltViewModel()
) {
    val lazyPagingItems = placeViewModel.pagingByTagIds(emptyList()).collectAsLazyPagingItems()

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(
            items = lazyPagingItems,
            key = { it.entity.id }
        ) {
            PlacePreviewCompose(
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
private fun AddButton(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = {
            navController.navigate(DeepLink.Place.getPlaceDetailAction())
        }
    ) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = stringResource(id = StringResource.add)
        )
    }
}