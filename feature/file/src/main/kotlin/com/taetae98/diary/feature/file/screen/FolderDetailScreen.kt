package com.taetae98.diary.feature.file.screen

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.taetae98.diary.feature.compose.diary.DiaryTopAppBar
import com.taetae98.diary.feature.compose.diary.DiaryTopAppBarNavigationIcon
import com.taetae98.diary.feature.compose.input.ClearTextField
import com.taetae98.diary.feature.compose.input.PasswordInputCompose
import com.taetae98.diary.feature.file.event.FolderDetailEvent
import com.taetae98.diary.feature.file.viewmodel.FolderDetailViewModel
import com.taetae98.diary.feature.resource.StringResource
import kotlinx.coroutines.launch

@Composable
fun FolderDetailScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = { FolderDetailTopAppBar(navController = navController) },
        floatingActionButton = { FAB() }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FolderLayout()
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
    folderDetailViewModel: FolderDetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        folderDetailViewModel.event.collect {
            when (it) {
                is FolderDetailEvent.Error -> snackbarHostState.showSnackbar(
                    message = "Error ${it.throwable.message}",
                )
                FolderDetailEvent.Edit -> {
                    if (folderDetailViewModel.isUpdateMode()) {
                        navController.navigateUp()
                    } else {
                        launch {
                            snackbarHostState.currentSnackbarData?.dismiss()
                            snackbarHostState.showSnackbar(
                                context.getString(StringResource.add)
                            )
                        }

                        folderDetailViewModel.clear()
                    }
                }
                else -> Unit
            }
        }
    }
}

@Composable
private fun FolderDetailTopAppBar(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    DiaryTopAppBar(
        modifier = modifier,
        navigationIcon = {
            DiaryTopAppBarNavigationIcon(navController = navController)
        }
    )
}

@Composable
private fun FolderLayout(
    modifier: Modifier = Modifier,
    folderDetailViewModel: FolderDetailViewModel = hiltViewModel()
) {
    val focusRequester = remember { FocusRequester() }
    Card(modifier = modifier) {
        ClearTextField(
            modifier = Modifier.focusRequester(focusRequester),
            value = folderDetailViewModel.title.collectAsState().value,
            onValueChange = folderDetailViewModel::setTitle,
            label = stringResource(id = StringResource.title)
        )
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        folderDetailViewModel.event.collect {
            if (it == FolderDetailEvent.NoTitle) {
                focusRequester.requestFocus()
            }
        }
    }
}

@Composable
private fun PasswordLayout(
    modifier: Modifier = Modifier,
    folderDetailViewModel: FolderDetailViewModel = hiltViewModel()
) {
    Card(modifier = modifier) {
        PasswordInputCompose(
            hasPassword = folderDetailViewModel.hasPassword.collectAsState().value,
            onHasPasswordChanged = folderDetailViewModel::setHasPassword,
            password = folderDetailViewModel.password.collectAsState().value,
            onPasswordChanged = folderDetailViewModel::setPassword
        )
    }
}

@Composable
private fun FAB(
    modifier: Modifier = Modifier,
    folderDetailViewModel: FolderDetailViewModel = hiltViewModel()
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = { folderDetailViewModel.edit() }
    ) {
        if (folderDetailViewModel.isUpdateMode()) {
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