package com.taetae98.diary.feature.more

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Android
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.taetae98.diary.feature.compose.input.PasswordDialog
import com.taetae98.diary.feature.compose.diary.DiaryTopAppBar
import com.taetae98.diary.feature.developer.DeveloperGraph
import com.taetae98.diary.feature.resource.StringResource
import com.taetae98.diary.feature.setting.SettingGraph
import com.taetae98.diary.feature.theme.DiaryTheme

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
        Column(
            modifier = Modifier
                .padding(it)
                .padding(8.dp)
        ) {
            DeveloperCompose(navController = navController)
        }
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
                    navController.navigate(SettingGraph.getAction())
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

@Composable
private fun DeveloperCompose(
    modifier: Modifier = Modifier,
    navController: NavController,
    moreViewModel: MoreViewModel = hiltViewModel()
) {
    val (clickCount, setClickCount) = remember { mutableStateOf(0) }
    val (isPasswordDialogVisible, setPasswordDialogVisible) = remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .clickable {
                if (clickCount + 1 == 10) {
                    moreViewModel.setIsDeveloperModeEnable(true)
                } else {
                    setClickCount(clickCount + 1)
                }
            }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Rounded.Email,
                contentDescription = stringResource(id = StringResource.email)
            )
            Text(
                modifier = Modifier.weight(1F),
                text = stringResource(id = StringResource.developer_email)
            )

            if (moreViewModel.isDeveloperModeEnable.collectAsState().value) {
                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = {
                        if (moreViewModel.developerModePassword.value == null) {
                            navController.navigate(DeveloperGraph.getAction())
                        } else {
                            setPasswordDialogVisible(true)
                        }
                    },
                    enabled = moreViewModel.isDeveloperModeEnable.collectAsState().value
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Android,
                        contentDescription = stringResource(id = StringResource.developer)
                    )
                }
            }
        }
    }

    if (isPasswordDialogVisible) {
        moreViewModel.developerModePassword.collectAsState().value?.let { password ->
            PasswordDialog(
                password = password,
                onSuccess = { navController.navigate(DeveloperGraph.getAction()) },
                onDismissRequest = { setPasswordDialogVisible(false) }
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    DiaryTheme {
        MoreScreen(navController = rememberNavController())
    }
}