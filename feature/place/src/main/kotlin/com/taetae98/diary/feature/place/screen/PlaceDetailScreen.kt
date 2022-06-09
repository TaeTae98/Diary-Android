package com.taetae98.diary.feature.place.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.taetae98.diary.domain.model.place.PlaceEntity
import com.taetae98.diary.feature.common.DeepLink
import com.taetae98.diary.feature.common.Parameter
import com.taetae98.diary.feature.common.util.getResult
import com.taetae98.diary.feature.common.util.removeResult
import com.taetae98.diary.feature.common.util.setResult
import com.taetae98.diary.feature.compose.diary.DiaryMap
import com.taetae98.diary.feature.compose.diary.DiaryTopAppBar
import com.taetae98.diary.feature.compose.diary.DiaryTopAppBarNavigationIcon
import com.taetae98.diary.feature.compose.input.ClearTextField
import com.taetae98.diary.feature.compose.input.PasswordInputCompose
import com.taetae98.diary.feature.compose.sideeffct.OnLifecycle
import com.taetae98.diary.feature.place.R
import com.taetae98.diary.feature.place.event.PlaceDetailEvent
import com.taetae98.diary.feature.place.viewmodel.PlaceDetailViewModel
import com.taetae98.diary.feature.resource.StringResource
import kotlinx.coroutines.launch

@Composable
fun PlaceDetailScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = { PlaceEditTopBar(navController = navController) },
        floatingActionButton = { FAB() }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PlaceLayout(snackbarHostState = scaffoldState.snackbarHostState)
            PasswordLayout()
            MapLayout()
        }
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
    placeDetailViewModel: PlaceDetailViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        placeDetailViewModel.event.collect {
            when (it) {
                is PlaceDetailEvent.Edit -> {
                    navController.setResult(Parameter.PLACE, it.entity)
                    navController.navigateUp()
                }
                PlaceDetailEvent.NoPlace -> snackbarHostState.showSnackbar(
                    message = context.getString(R.string.select_place),
                )
                is PlaceDetailEvent.Error -> {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    launch {
                        snackbarHostState.showSnackbar(
                            message = "Error ${it.throwable.message}"
                        )
                    }
                }
                else -> Unit
            }
        }
    }

    val entity = navController.getResult<PlaceEntity>(Parameter.PLACE).value
    OnLifecycle { event ->
        when(event) {
            Lifecycle.Event.ON_START -> {
                entity?.let {
                    placeDetailViewModel.update(it)
                    navController.removeResult(Parameter.PLACE)
                }
            }
            else -> Unit
        }
    }
}

@Composable
private fun PlaceEditTopBar(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    DiaryTopAppBar(
        modifier = modifier,
        navigationIcon = { DiaryTopAppBarNavigationIcon(navController = navController) },
        actions = {
            IconButton(
                onClick = {
                    navController.navigate(DeepLink.Place.PLACE_SEARCH_URL)
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = stringResource(id = StringResource.search)
                )
            }
        }
    )
}

@Composable
private fun PlaceLayout(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column {
            TitleInput(snackbarHostState = snackbarHostState)
            AddressInput()
            Divider()
            LinkInput()
            DescriptionInput()
        }
    }
}

@Composable
private fun TitleInput(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    placeDetailViewModel: PlaceDetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }

    ClearTextField(
        modifier = modifier
            .focusRequester(focusRequester),
        value = placeDetailViewModel.title.collectAsState().value,
        onValueChange = placeDetailViewModel::setTitle,
        label = stringResource(id = StringResource.title),
        singleLine = true,
        maxLines = 1
    )

    LaunchedEffect(Unit) {
        placeDetailViewModel.event.collect {
            when (it) {
                is PlaceDetailEvent.NoTitle -> {
                    focusRequester.requestFocus()
                    snackbarHostState.currentSnackbarData?.dismiss()
                    launch {
                        snackbarHostState.showSnackbar(
                            message = context.getString(StringResource.title_is_empty)
                        )
                    }
                }
                else -> Unit
            }
        }
    }
}

@Composable
private fun AddressInput(
    modifier: Modifier = Modifier,
    placeDetailViewModel: PlaceDetailViewModel = hiltViewModel()
) {
    ClearTextField(
        modifier = modifier,
        value = placeDetailViewModel.address.collectAsState().value,
        onValueChange = placeDetailViewModel::setAddress,
        label = stringResource(id = StringResource.address),
        singleLine = true,
        maxLines = 1
    )
}

@Composable
private fun LinkInput(
    modifier: Modifier = Modifier,
    placeDetailViewModel: PlaceDetailViewModel = hiltViewModel()
) {
    ClearTextField(
        modifier = modifier,
        value = placeDetailViewModel.link.collectAsState().value,
        onValueChange = placeDetailViewModel::setLink,
        label = stringResource(id = StringResource.link),
        singleLine = true,
        maxLines = 1
    )
}

@Composable
private fun DescriptionInput(
    modifier: Modifier = Modifier,
    placeDetailViewModel: PlaceDetailViewModel = hiltViewModel()
) {
    ClearTextField(
        modifier = modifier,
        value = placeDetailViewModel.description.collectAsState().value,
        onValueChange = placeDetailViewModel::setDescription,
        label = stringResource(id = StringResource.description)
    )
}

@Composable
private fun PasswordLayout(
    modifier: Modifier = Modifier,
    placeDetailViewModel: PlaceDetailViewModel = hiltViewModel()
) {
    Card(modifier = modifier) {
        PasswordInputCompose(
            hasPassword = placeDetailViewModel.hasPassword.collectAsState().value,
            onHasPasswordChanged = placeDetailViewModel::setHasPassword,
            password = placeDetailViewModel.password.collectAsState().value,
            onPasswordChanged = placeDetailViewModel::setPassword
        )
    }
}

@Composable
private fun MapLayout(
    modifier: Modifier = Modifier,
    placeDetailViewModel: PlaceDetailViewModel = hiltViewModel()
) {
    Card(
        modifier = modifier
    ) {
        DiaryMap(
            pin = placeDetailViewModel.pin.collectAsState().value?.let { listOf(it) } ?: emptyList(),
            onMapClickListener = { placeDetailViewModel.update(it) }
        )
    }
}

@Composable
private fun FAB(
    modifier: Modifier = Modifier,
    placeDetailViewModel: PlaceDetailViewModel = hiltViewModel()
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = {
            placeDetailViewModel.execute()
        }
    ) {
        if (placeDetailViewModel.isUpdateMode()) {
            Icon(
                imageVector = Icons.Rounded.Check,
                contentDescription = stringResource(id = StringResource.edit)
            )
        } else {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = stringResource(id = StringResource.add)
            )
        }
    }
}