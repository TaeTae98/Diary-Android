package com.taetae98.diary.feature.memo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.taetae98.diary.feature.common.Parameter
import com.taetae98.diary.feature.compose.input.ClearTextField
import com.taetae98.diary.feature.compose.input.PasswordInputCompose
import com.taetae98.diary.feature.compose.diary.DiaryTopAppBar
import com.taetae98.diary.feature.compose.diary.DiaryTopAppBarNavigationIcon
import com.taetae98.diary.feature.resource.StringResource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

object MemoEditScreen {
    private const val ROUTE_PATH = "MemoEditScreen"

    const val ROUTE = "$ROUTE_PATH?${Parameter.MEMO_ID}={${Parameter.MEMO_ID}}"

    fun getAction(memoId: Int = 0): String {
        return "$ROUTE_PATH?${Parameter.MEMO_ID}=$memoId"
    }
}

@Composable
fun MemoEditScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = { MemoEditTopAppBar(navController = navController) },
        floatingActionButton = { FAB() }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MemoLayout()
            PasswordLayout()
        }
    }

    CollectMemoEditEvent(
        snackbarHostState = scaffoldState.snackbarHostState,
        navController = navController
    )
}

@Composable
private fun MemoEditTopAppBar(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    DiaryTopAppBar(
        modifier = modifier,
        navigationIcon = { DiaryTopAppBarNavigationIcon(navController = navController) }
    )
}

@Composable
private fun CollectMemoEditEvent(
    snackbarHostState: SnackbarHostState,
    navController: NavController,
    memoEditViewModel: MemoEditViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        memoEditViewModel.event.collect {
            when (it) {
                is MemoEditEvent.Success -> {
                    if (memoEditViewModel.isEditMode()) {
                        navController.navigateUp()
                    } else {
                        memoEditViewModel.clear()
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
                is MemoEditEvent.Error -> {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(
                        message = "Error : ${it.throwable.message}"
                    )
                }
                is MemoEditEvent.TitleEmpty -> {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    launch {
                        snackbarHostState.showSnackbar(message = context.getString(R.string.title_is_empty))
                    }
                }
                else -> Unit
            }
        }
    }
}

@Composable
private fun MemoLayout(
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column {
            TitleInput(modifier = Modifier.fillMaxWidth())
            DescriptionInput(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun TitleInput(
    modifier: Modifier = Modifier,
    memoEditViewModel: MemoEditViewModel = hiltViewModel()
) {
    val focusRequester = remember { FocusRequester() }
    val (isError, setIsError) = remember { mutableStateOf(false) }

    ClearTextField(
        modifier = modifier.focusRequester(focusRequester),
        value = memoEditViewModel.title.collectAsState().value,
        onValueChange = {
            setIsError(false)
            memoEditViewModel.setTitle(it)
        },
        label = stringResource(id = StringResource.title),
        isError = isError,
        singleLine = true,
        maxLines = 1
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        memoEditViewModel.event.collect {
            when (it) {
                is MemoEditEvent.TitleEmpty -> {
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
    memoEditViewModel: MemoEditViewModel = hiltViewModel()
) {
    ClearTextField(
        modifier = modifier,
        value = memoEditViewModel.description.collectAsState().value,
        onValueChange = memoEditViewModel::setDescription,
        label = stringResource(id = StringResource.description),
        singleLine = true,
        maxLines = 1
    )
}

@Composable
private fun PasswordLayout(
    modifier: Modifier = Modifier,
    memoEditViewModel: MemoEditViewModel = hiltViewModel()
) {
    Card(modifier = modifier) {
        PasswordInputCompose(
            hasPassword = memoEditViewModel.hasPassword.collectAsState().value,
            onHasPasswordChanged = memoEditViewModel::setHasPassword,
            password = memoEditViewModel.password.collectAsState().value,
            onPasswordChanged = memoEditViewModel::setPassword
        )
    }
}

@Composable
private fun FAB(
    modifier: Modifier = Modifier,
    memoEditViewModel: MemoEditViewModel = hiltViewModel()
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = { memoEditViewModel.edit() }
    ) {
        if (memoEditViewModel.isEditMode()) {
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