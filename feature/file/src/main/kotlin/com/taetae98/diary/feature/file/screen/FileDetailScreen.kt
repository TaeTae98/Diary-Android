package com.taetae98.diary.feature.file.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.taetae98.diary.feature.common.util.isFalse
import com.taetae98.diary.feature.compose.diary.DiaryTopAppBar
import com.taetae98.diary.feature.compose.diary.DiaryTopAppBarNavigationIcon
import com.taetae98.diary.feature.compose.input.ClearTextField
import com.taetae98.diary.feature.compose.input.PasswordInputCompose
import com.taetae98.diary.feature.file.event.FileDetailEvent
import com.taetae98.diary.feature.file.viewmodel.FileDetailViewModel
import com.taetae98.diary.feature.resource.StringResource
import kotlinx.coroutines.launch

@Composable
fun FileDetailScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = { FileDetailTopAppBar(navController = navController) },
        floatingActionButton = { FAB() }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FileDetailLayout()
            PasswordLayout()
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
    fileDetailViewModel: FileDetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        fileDetailViewModel.event.collect {
            when(it) {
                is FileDetailEvent.Error -> snackbarHostState.showSnackbar(
                    "Error : ${it.throwable.message}"
                )
                FileDetailEvent.Insert -> {
                    launch {
                        snackbarHostState.currentSnackbarData?.dismiss()
                        snackbarHostState.showSnackbar(context.getString(StringResource.add))
                    }
                    fileDetailViewModel.clear()
                }
                FileDetailEvent.Update -> navController.navigateUp()
                else -> Unit
            }
        }
    }
}

@Composable
private fun FileDetailLayout(
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
    fileDetailViewModel: FileDetailViewModel = hiltViewModel()
) {
    val focusRequester = remember { FocusRequester() }
    val (isError, setIsError) = remember { mutableStateOf(false) }

    ClearTextField(
        modifier = modifier
            .focusRequester(focusRequester),
        value = fileDetailViewModel.title.collectAsState().value,
        onValueChange = {
            setIsError(false)
            fileDetailViewModel.setTitle(it)
        },
        label = stringResource(id = StringResource.title),
        isError = isError,
        singleLine = true,
        maxLines = 1
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        fileDetailViewModel.event.collect {
            when (it) {
                is FileDetailEvent.NoTitle -> {
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
    fileDetailViewModel: FileDetailViewModel = hiltViewModel()
) {
    ClearTextField(
        modifier = modifier,
        value = fileDetailViewModel.description.collectAsState().value,
        onValueChange = fileDetailViewModel::setDescription,
        label = stringResource(id = StringResource.description),
        singleLine = true,
        maxLines = 1
    )
}

@Composable
private fun PasswordLayout(
    modifier: Modifier = Modifier,
    fileDetailViewModel: FileDetailViewModel = hiltViewModel()
) {
    Card(modifier = modifier) {
        PasswordInputCompose(
            hasPassword = fileDetailViewModel.hasPassword.collectAsState().value,
            onHasPasswordChanged = fileDetailViewModel::setHasPassword,
            password = fileDetailViewModel.password.collectAsState().value,
            onPasswordChanged = fileDetailViewModel::setPassword
        )
    }
}

@Composable
private fun FileDetailTopAppBar(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    DiaryTopAppBar(
        modifier = modifier,
        navigationIcon = { DiaryTopAppBarNavigationIcon(navController = navController) }
    )
}

@Composable
private fun FAB(
    modifier: Modifier = Modifier,
    fileDetailViewModel: FileDetailViewModel = hiltViewModel()
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ) {
        it?.let { fileDetailViewModel.insert(it) }
    }

    FloatingActionButton(
        modifier = modifier,
        onClick = {
            if (fileDetailViewModel.isUpdateMode()) {
                fileDetailViewModel.update()
            } else {
                if (fileDetailViewModel.isTitleEmpty().isFalse()) {
                    launcher.launch("*/*")
                }
            }
        }
    ) {
        if (fileDetailViewModel.isUpdateMode()) {
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
