package com.taetae98.diary.feature.memo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.taetae98.diary.feature.compose.DiaryTopAppBar
import com.taetae98.diary.feature.resource.StringResource
import com.taetae98.diary.feature.theme.DiaryTheme

object MemoScreen {
    const val ROUTE = "MemoScreen"
}

@Composable
fun MemoScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    Scaffold(
        modifier = modifier,
        topBar = { MemoTopAppBar(navController = navController) }
    ) {
        MemoLazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        )
    }
}

@Composable
private fun MemoTopAppBar(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    DiaryTopAppBar(
        modifier = modifier,
        title = { Text(text = stringResource(id = StringResource.memo)) },
        actions = {
            IconButton(
                onClick = { navController.navigate(MemoEditScreen.getAction()) }
            ) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = stringResource(id = StringResource.add))
            }
        }
    )
}

@Composable
private fun MemoLazyColumn(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = List(100) { "index : $it" },
            key = { it }
        ) {
            Card {
                Text(text = it)
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    DiaryTheme {
        MemoScreen(
            navController = rememberNavController()
        )
    }
}