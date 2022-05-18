package com.taetae98.diary.feature.compose

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.taetae98.diary.feature.theme.DiaryTheme

@Composable
fun DiaryTopAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = DiaryTheme.colors.primary,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = 0.dp,
) {
    TopAppBar(
        modifier = modifier,
        title = title,
        navigationIcon = navigationIcon,
        actions = {
            CompositionLocalProvider(values = arrayOf(LocalContentAlpha provides 1F)) {
                actions()
            }
        },
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        elevation = elevation,
    )
}

@Preview(
    name = "DiaryTopAppBar"
)
@Composable
private fun Preview() {
    DiaryTheme {
        DiaryTopAppBar(
            title = {
                Text(text = "Preview")
            },
            navigationIcon = {
                IconButton(
                    onClick = { }
                ) {
                    Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
                }
            },
            actions = {
                IconButton(
                    onClick = { }
                ) {
                    Icon(imageVector = Icons.Rounded.MoreHoriz, contentDescription = null)
                }
            }
        )
    }
}