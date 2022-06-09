package com.taetae98.diary.feature.memo.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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
import com.taetae98.diary.feature.compose.diary.DiaryMap
import com.taetae98.diary.feature.compose.diary.DiaryTopAppBar
import com.taetae98.diary.feature.compose.diary.DiaryTopAppBarNavigationIcon
import com.taetae98.diary.feature.compose.input.ClearTextField
import com.taetae98.diary.feature.compose.input.PasswordInputCompose
import com.taetae98.diary.feature.compose.modifier.swipeable
import com.taetae98.diary.feature.compose.place.PlaceHeaderCompose
import com.taetae98.diary.feature.compose.sideeffct.OnLifecycle
import com.taetae98.diary.feature.memo.event.MemoDetailEvent
import com.taetae98.diary.feature.memo.viewmodel.MemoDetailViewModel
import com.taetae98.diary.feature.resource.StringResource
import kotlinx.coroutines.launch

@Composable
fun MemoDetailScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = { MemoDetailTopAppBar(navController = navController) },
        floatingActionButton = { FAB() }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MemoLayout()
            PasswordLayout()
            MapLayout(navController = navController)
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
    memoDetailViewModel: MemoDetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        memoDetailViewModel.event.collect {
            when (it) {
                is MemoDetailEvent.Edit -> {
                    if (memoDetailViewModel.isEditMode()) {
                        navController.navigateUp()
                    } else {
                        memoDetailViewModel.clear()
                        snackbarHostState.currentSnackbarData?.dismiss()
                        launch {
                            snackbarHostState.showSnackbar(
                                message = context.getString(
                                    StringResource.add
                                )
                            )
                        }
                    }
                }
                is MemoDetailEvent.Error -> {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(
                        message = "Error : ${it.throwable.message}"
                    )
                }
                is MemoDetailEvent.NoTitle -> {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    launch {
                        snackbarHostState.showSnackbar(message = context.getString(StringResource.title_is_empty))
                    }
                }
            }
        }
    }

    CollectPlaceEntity(navController = navController)
}

@Composable
private fun CollectPlaceEntity(
    navController: NavController,
    memoDetailViewModel: MemoDetailViewModel = hiltViewModel()
) {
    val entity = navController.getResult<PlaceEntity>(Parameter.PLACE).value
    OnLifecycle { event ->
        when (event) {
            Lifecycle.Event.ON_START -> {
                entity?.let {
                    memoDetailViewModel.add(it)
                    navController.removeResult(Parameter.PLACE)
                }
            }
            else -> Unit
        }
    }
}

@Composable
private fun MemoDetailTopAppBar(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    DiaryTopAppBar(
        modifier = modifier,
        navigationIcon = { DiaryTopAppBarNavigationIcon(navController = navController) }
    )
}

@Composable
private fun MemoLayout(
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column {
            TitleInput()
            DescriptionInput()
        }
    }
}

@Composable
private fun TitleInput(
    modifier: Modifier = Modifier,
    memoDetailViewModel: MemoDetailViewModel = hiltViewModel()
) {
    val focusRequester = remember { FocusRequester() }
    val (isError, setIsError) = remember { mutableStateOf(false) }

    ClearTextField(
        modifier = modifier
            .focusRequester(focusRequester),
        value = memoDetailViewModel.title.collectAsState().value,
        onValueChange = {
            setIsError(false)
            memoDetailViewModel.setTitle(it)
        },
        label = stringResource(id = StringResource.title),
        isError = isError,
        singleLine = true,
        maxLines = 1
    )

    LaunchedEffect(Unit) {
//        TODO NaverMap 포커스 잃는 에러
//        focusRequester.requestFocus()
        memoDetailViewModel.event.collect {
            when (it) {
                is MemoDetailEvent.NoTitle -> {
                    setIsError(true)
                    focusRequester.requestFocus()
                }
                else -> Unit
            }
        }
    }
}

@Composable
private fun DescriptionInput(
    modifier: Modifier = Modifier,
    memoDetailViewModel: MemoDetailViewModel = hiltViewModel()
) {
    ClearTextField(
        modifier = modifier,
        value = memoDetailViewModel.description.collectAsState().value,
        onValueChange = memoDetailViewModel::setDescription,
        label = stringResource(id = StringResource.description),
        singleLine = true,
        maxLines = 1
    )
}

@Composable
private fun PasswordLayout(
    modifier: Modifier = Modifier,
    memoDetailViewModel: MemoDetailViewModel = hiltViewModel()
) {
    Card(modifier = modifier) {
        PasswordInputCompose(
            hasPassword = memoDetailViewModel.hasPassword.collectAsState().value,
            onHasPasswordChanged = memoDetailViewModel::setHasPassword,
            password = memoDetailViewModel.password.collectAsState().value,
            onPasswordChanged = memoDetailViewModel::setPassword
        )
    }
}

@Composable
private fun MapLayout(
    modifier: Modifier = Modifier,
    navController: NavController,
    memoDetailViewModel: MemoDetailViewModel = hiltViewModel()
) {
    Card(modifier = modifier) {
        Column {
            DiaryMap(
                modifier = Modifier.height(250.dp),
                pin = memoDetailViewModel.place.collectAsState().value,
                camera = memoDetailViewModel.camera.collectAsState().value
            )
            PlaceList(navController = navController)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun PlaceList(
    modifier: Modifier = Modifier,
    navController: NavController,
    memoDetailViewModel: MemoDetailViewModel = hiltViewModel()
) {
    val items = memoDetailViewModel.placeUiState.collectAsState().value
    LazyColumn(
        modifier = modifier.heightIn(max = 400.dp)
    ) {
        items(
            items = items,
            key = { it.entity.id }
        ) {
            PlaceHeaderCompose(
                modifier = Modifier
                    .clickable(
                        onClickLabel = it.entity.title,
                        onClick = it.onClick
                    )
                    .swipeable { _, targetValue ->
                        if (targetValue != 0) it.onDelete()
                    },
                entity = it.entity
            )
        }

        item {
            PlaceAddButton(navController = navController)
        }
    }
}

@Composable
private fun PlaceAddButton(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .padding(0.dp),
        onClick = {
            navController.navigate(DeepLink.Memo.getPlaceSelectAction())
        },
        elevation = null,
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
    ) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = stringResource(id = StringResource.add)
        )
    }
}

@Composable
private fun FAB(
    modifier: Modifier = Modifier,
    memoDetailViewModel: MemoDetailViewModel = hiltViewModel()
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = memoDetailViewModel::edit
    ) {
        if (memoDetailViewModel.isEditMode()) {
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