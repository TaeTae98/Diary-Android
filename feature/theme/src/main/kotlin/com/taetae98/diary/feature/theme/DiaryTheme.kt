package com.taetae98.diary.feature.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object DiaryTheme {
    val primaryColor: Color
        @Composable
        get() = if (isSystemInDarkTheme()) {
            Color(0xFF3C5099)
        } else {
            Color(0xFF3C5099)
        }

    val backgroundColor: Color
        @Composable
        get() = primaryColor

    val colors: Colors
        @Composable
        get() = if (isSystemInDarkTheme()) {
            darkColors()
        } else {
            lightColors(
                primary = primaryColor,
                background = backgroundColor,
            )
        }

    val typography = Typography()
    val shapes = Shapes()
}

@Composable
fun DiaryTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = DiaryTheme.colors,
        typography = DiaryTheme.typography,
        shapes = DiaryTheme.shapes,
        content = content,
    )
}