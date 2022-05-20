package com.taetae98.diary.feature.setting

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.taetae98.diary.feature.common.isFalse
import com.taetae98.diary.feature.compose.DiaryTopAppBar
import com.taetae98.diary.feature.compose.canDrawOverlays
import com.taetae98.diary.feature.compose.isBatteryOptimized
import com.taetae98.diary.feature.resource.StringResource
import com.taetae98.diary.feature.theme.DiaryTheme
import kotlinx.coroutines.flow.collect

object SettingScreen {
    const val ROUTE = "SettingScreen"

    fun getAction(): String {
        return ROUTE
    }
}

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = { SettingTopAppBar(navController = navController) }
    ) {
        Column {
            RunOnUnlockLayout(
                modifier = Modifier
                    .padding(it)
                    .padding(8.dp)
                    .fillMaxWidth()
            )
        }
    }

    CollectEvent(snackbarHostState = scaffoldState.snackbarHostState)
}

@Composable
private fun CollectEvent(
    snackbarHostState: SnackbarHostState,
    settingViewModel: SettingViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        settingViewModel.event.collect {
            when(it) {
                is SettingEvent.Error -> {
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
private fun SettingTopAppBar(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    DiaryTopAppBar(
        modifier = modifier,
        title = { Text(text = stringResource(id = StringResource.setting)) },
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.navigateUp()
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = stringResource(id = StringResource.back)
                )
            }
        }
    )
}

@Composable
private fun RunOnUnlockLayout(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
    ) {
        Column {
            RunOnUnlockHeaderLayout()
            Divider()
            RunOnUnlockOptimizationLayout()
        }
    }
}

@Composable
private fun RunOnUnlockHeaderLayout(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        RunOnUnlock()
        RunOnUnlockDescription()
    }
}

@Composable
private fun RunOnUnlock(
    modifier: Modifier = Modifier,
    settingViewModel: SettingViewModel = hiltViewModel()
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1F)
                .padding(8.dp),
            text = stringResource(id = StringResource.setting_run_on_unlock)
        )
        Switch(
            checked = settingViewModel.isRunOnUnlock.collectAsState().value,
            onCheckedChange = { settingViewModel.setIsRunOnUnlock(it) },
            enabled = canDrawOverlays(defaultValue = false)
        )
    }
}

@Composable
private fun RunOnUnlockDescription(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    if (canDrawOverlays(defaultValue = true).isFalse()) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1F)
                    .padding(8.dp)
            ) {
                Text(text = stringResource(id = StringResource.setting_run_on_unlock_description))
            }
            IconButton(
                onClick = {
                    Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:${context.packageName}")
                    ).also {
                        context.startActivity(it)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.ChevronRight,
                    contentDescription = stringResource(id = StringResource.setting)
                )
            }
        }
    }
}

@Composable
private fun RunOnUnlockOptimizationLayout(
    modifier: Modifier = Modifier,
    settingViewModel: SettingViewModel = hiltViewModel()
) {
    if (settingViewModel.isRunOnUnlock.collectAsState().value.isFalse()) {
        return
    }

    Column(
        modifier = modifier
    ) {
        RunOnUnlockOptimization()
        RunOnUnlockOptimizationDescription()
    }
}

@Composable
private fun RunOnUnlockOptimization(
    modifier: Modifier = Modifier,
    settingViewModel: SettingViewModel = hiltViewModel()
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1F)
                .padding(8.dp),
            text = stringResource(id = StringResource.setting_run_on_unlock_hide_notification)
        )
        Switch(
            checked = settingViewModel.isRunOnUnlockOptimized.collectAsState().value,
            onCheckedChange = {
                settingViewModel.setIsRunOnUnlockOptimized(it)
            },
            enabled = isBatteryOptimized(defaultValue = true).not()
        )
    }
}

@Composable
private fun RunOnUnlockOptimizationDescription(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    if (isBatteryOptimized(defaultValue = false)) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1F)
                    .padding(8.dp)
            ) {
                Text(text = stringResource(id = StringResource.setting_run_on_unlock_hide_notification_description))
                Text(text = stringResource(id = StringResource.setting_run_on_unlock_hide_notification_description_hide_notification))
                Text(text = stringResource(id = StringResource.setting_run_on_unlock_hide_notification_description_because_of_google_policy))
            }
            IconButton(
                onClick = {
                    Intent(
                        Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS,
                    ).also {
                        context.startActivity(it)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.ChevronRight,
                    contentDescription = stringResource(id = StringResource.setting)
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    DiaryTheme {
        SettingScreen(
            navController = rememberNavController()
        )
    }
}