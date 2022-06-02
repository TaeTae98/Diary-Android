package com.taetae98.diary.feature.place.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.taetae98.diary.domain.model.PlaceEntity
import com.taetae98.diary.feature.compose.diary.DiaryTopAppBar
import com.taetae98.diary.feature.compose.input.PasswordDialog
import com.taetae98.diary.feature.place.compose.PlaceCompose
import com.taetae98.diary.feature.place.event.PlaceEvent
import com.taetae98.diary.feature.place.viewmodel.PlaceViewModel
import com.taetae98.diary.feature.resource.StringResource
import kotlinx.coroutines.flow.collect

object PlaceScreen {
    const val ROUTE = "PlaceScreen"
}

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
    val (entity, setEntity) = remember { mutableStateOf<PlaceEntity?>(null) }
    val (isPasswordDialogVisible, setPasswordDialogVisible) = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        placeViewModel.event.collect {
            when (it) {
                is PlaceEvent.Detail -> {
                    if (it.entity.password == null) {
                        navController.navigate(PlaceDetailScreen.getAction(it.entity.id))
                    } else {
                        setEntity(it.entity)
                        setPasswordDialogVisible(true)
                    }
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

    if (isPasswordDialogVisible && entity != null) {
        val password = entity.password ?: return
        PasswordDialog(
            password = password,
            onSuccess = { navController.navigate(PlaceDetailScreen.getAction(entity.id)) },
            onDismissRequest = { setPasswordDialogVisible(false) }
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
            PlaceCompose(
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
            navController.navigate(PlaceDetailScreen.getAction())
        }
    ) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = stringResource(id = StringResource.add)
        )
    }
}