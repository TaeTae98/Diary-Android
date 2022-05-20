package com.taetae98.diary.feature.more

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.taetae98.diary.feature.compose.DiaryTopAppBar
import com.taetae98.diary.feature.resource.StringResource
import com.taetae98.diary.feature.setting.SettingScreen

object MoreScreen {
    const val ROUTE = "MoreScreen"
}

@Composable
fun MoreScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Scaffold(
        modifier = modifier,
        topBar = { MoreTopAppBar(navController = navController) }
    ) {

    }
}

@Composable
private fun MoreTopAppBar(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    DiaryTopAppBar(
        modifier = modifier,
        title = { Text(text = stringResource(id = StringResource.more)) },
        actions = {
            IconButton(
                onClick = {
                    navController.navigate(SettingScreen.getAction())
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Settings,
                    contentDescription = stringResource(id = StringResource.setting)
                )
            }
        }
    )
}