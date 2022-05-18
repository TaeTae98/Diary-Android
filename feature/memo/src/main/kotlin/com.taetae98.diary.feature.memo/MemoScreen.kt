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
import com.taetae98.diary.feature.compose.DiaryTopAppBar
import com.taetae98.diary.feature.theme.DiaryTheme
import com.taetae98.resource.StringResource

@Composable
fun MemoScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = { MemoTopAppBar() }
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
    modifier: Modifier = Modifier
) {
    DiaryTopAppBar(
        modifier = modifier,
        title = { Text(text = stringResource(id = StringResource.memo)) },
        actions = {
            IconButton(
                onClick = {  }
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

@Preview(
    name = "MemoScreen"
)
@Composable
private fun Preview() {
    DiaryTheme {
        MemoScreen()
    }
}