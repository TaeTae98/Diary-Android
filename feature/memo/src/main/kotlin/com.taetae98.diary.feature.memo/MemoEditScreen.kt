package com.taetae98.diary.feature.memo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material.icons.rounded.VpnKey
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.taetae98.diary.feature.common.Parameter
import com.taetae98.diary.feature.common.isFalse
import com.taetae98.diary.feature.compose.DiaryTextField
import com.taetae98.diary.feature.compose.DiaryTopAppBar
import com.taetae98.diary.feature.resource.StringResource
import com.taetae98.diary.feature.theme.DiaryTheme
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
                is MemoEditEvent.TitleEmpty -> {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    launch {
                        snackbarHostState.showSnackbar(message = context.getString(StringResource.message_title_is_empty))
                    }
                }
            }
        }
    }
}

@Composable
private fun MemoEditTopAppBar(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    DiaryTopAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = stringResource(id = StringResource.memo)
                )
            }
        }
    )
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
    var isError by remember { mutableStateOf(false) }

    DiaryTextField(
        modifier = modifier
            .focusRequester(focusRequester),
        value = memoEditViewModel.title.collectAsState().value,
        onValueChange = {
            isError = false
            memoEditViewModel.setTitle(it)
        },
        label = { Text(text = stringResource(id = StringResource.title)) },
        trailingIcon = {
            IconButton(
                onClick = {
                    memoEditViewModel.setTitle("")
                    focusRequester.requestFocus()
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Clear,
                    contentDescription = stringResource(id = StringResource.clear)
                )
            }
        },
        isError = isError,
        singleLine = true,
        maxLines = 1,
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        memoEditViewModel.event.collect {
            when (it) {
                is MemoEditEvent.TitleEmpty -> {
                    isError = true
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
    val focusRequester = remember { FocusRequester() }

    DiaryTextField(
        modifier = modifier
            .focusRequester(focusRequester),
        value = memoEditViewModel.description.collectAsState().value,
        onValueChange = { memoEditViewModel.setDescription(it) },
        label = { Text(text = stringResource(id = StringResource.description)) },
        trailingIcon = {
            IconButton(
                onClick = {
                    memoEditViewModel.setDescription("")
                    focusRequester.requestFocus()
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Clear,
                    contentDescription = stringResource(id = StringResource.clear)
                )
            }
        },
        maxLines = 10
    )
}

@Composable
private fun PasswordLayout(
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column {
            PasswordHeader()
            PasswordInput(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun PasswordHeader(
    modifier: Modifier = Modifier,
    memoEditViewModel: MemoEditViewModel = hiltViewModel()
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1F)
                .padding(8.dp),
            text = stringResource(id = StringResource.password)
        )

        IconButton(
            onClick = {
                memoEditViewModel.toggleHasPassword()
            }
        ) {
            if (memoEditViewModel.hasPassword.collectAsState().value) {
                Icon(
                    tint = DiaryTheme.primaryColor,
                    imageVector = Icons.Rounded.VpnKey,
                    contentDescription = stringResource(id = StringResource.password)
                )
            } else {
                Icon(
                    imageVector = Icons.Rounded.VpnKey,
                    contentDescription = stringResource(id = StringResource.password)
                )
            }
        }
    }
}

@Composable
private fun PasswordInput(
    modifier: Modifier = Modifier,
    memoEditViewModel: MemoEditViewModel = hiltViewModel()
) {
    if (memoEditViewModel.hasPassword.collectAsState().value.isFalse()) {
        return
    }

    val focusRequester = remember { FocusRequester() }
    var isPasswordVisible by remember { mutableStateOf(false) }

    DiaryTextField(
        modifier = modifier.focusRequester(focusRequester),
        value = memoEditViewModel.password.collectAsState().value,
        onValueChange = { memoEditViewModel.setPassword(it) },
        trailingIcon = {
            IconButton(onClick = { isPasswordVisible = isPasswordVisible.not() }) {
                Icon(
                    imageVector = if (isPasswordVisible) Icons.Rounded.Visibility else Icons.Rounded.VisibilityOff,
                    contentDescription = stringResource(id = if (isPasswordVisible) StringResource.show else StringResource.hide)
                )
            }
        },
        singleLine = true,
        maxLines = 1,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )

    LaunchedEffect(memoEditViewModel.hasPassword.collectAsState().value) {
        focusRequester.requestFocus()
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