package com.taetae98.diary.feature.compose.diary

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.taetae98.diary.feature.resource.StringResource

@Composable
fun DiaryTopAppBarNavigationIcon(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    DiaryTopAppBarNavigationIcon(
        modifier = modifier
    ) {
        navController.navigateUp()
    }
}

@Composable
fun DiaryTopAppBarNavigationIcon(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = onNavigateUp
    ) {
        Icon(
            imageVector = Icons.Rounded.ArrowBack,
            contentDescription = stringResource(id = StringResource.back)
        )
    }
}