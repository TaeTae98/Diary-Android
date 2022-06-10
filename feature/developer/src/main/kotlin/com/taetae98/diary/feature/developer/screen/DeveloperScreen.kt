package com.taetae98.diary.feature.developer.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BugReport
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.SdCard
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.taetae98.diary.feature.common.DeepLink
import com.taetae98.diary.feature.compose.diary.DiarySwitch
import com.taetae98.diary.feature.compose.diary.DiaryTopAppBar
import com.taetae98.diary.feature.compose.diary.DiaryTopAppBarNavigationIcon
import com.taetae98.diary.feature.compose.input.PasswordInputCompose
import com.taetae98.diary.feature.developer.R
import com.taetae98.diary.feature.developer.event.DeveloperEvent
import com.taetae98.diary.feature.developer.viewmodel.DeveloperViewModel
import com.taetae98.diary.feature.resource.StringResource

@Composable
fun DeveloperScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = { DeveloperTopAppBar(navController = navController) }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DeveloperModeEnable()
            DeveloperModePassword()
            ExceptionLog(navController = navController)
            FileExplorer(navController = navController)
        }
    }

    CollectEvent(snackbarHostState = scaffoldState.snackbarHostState)
}

@Composable
private fun CollectEvent(
    snackbarHostState: SnackbarHostState,
    developerViewModel: DeveloperViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        developerViewModel.event.collect {
            when (it) {
                is DeveloperEvent.Error -> {
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
private fun DeveloperTopAppBar(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    DiaryTopAppBar(
        modifier = modifier,
        title = { Text(text = stringResource(id = StringResource.developer)) },
        navigationIcon = { DiaryTopAppBarNavigationIcon(navController = navController) }
    )
}

@Composable
private fun DeveloperModeEnable(
    modifier: Modifier = Modifier,
    developerViewModel: DeveloperViewModel = hiltViewModel()
) {
    Card(
        modifier = modifier,
    ) {
        DiarySwitch(
            text = stringResource(id = StringResource.enable),
            checked = developerViewModel.isDeveloperModeEnable.collectAsState().value,
            onCheckedChange = developerViewModel::setDeveloperModeEnable
        )
    }
}

@Composable
private fun DeveloperModePassword(
    modifier: Modifier = Modifier,
    developerViewModel: DeveloperViewModel = hiltViewModel()
) {
    Card(
        modifier = modifier
    ) {
        PasswordInputCompose(
            hasPassword = developerViewModel.hasPassword.collectAsState().value,
            onHasPasswordChanged = developerViewModel::setHasPassword,
            password = developerViewModel.password.collectAsState().value,
            onPasswordChanged = developerViewModel::setPassword
        )
    }
}

@Composable
private fun ExceptionLog(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    DeveloperMenu(
        modifier = modifier,
        icon = Icons.Rounded.BugReport,
        title = stringResource(id = R.string.exception_log)
    ) {
        navController.navigate(DeepLink.Developer.EXCEPTION_LOG_URL)
    }
}

@Composable
private fun FileExplorer(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    DeveloperMenu(
        modifier = modifier,
        icon = Icons.Rounded.SdCard,
        title = stringResource(id = R.string.file_explorer)
    ) {
        navController.navigate(DeepLink.Developer.FILE_EXPLORER_URL)
    }
}

@Composable
private fun DeveloperMenu(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(start = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title
            )
            Text(
                modifier = Modifier.weight(1F),
                text = title
            )
            IconButton(
                onClick = onClick
            ) {
                Icon(
                    imageVector = Icons.Rounded.ChevronRight,
                    contentDescription = title
                )
            }
        }
    }
}