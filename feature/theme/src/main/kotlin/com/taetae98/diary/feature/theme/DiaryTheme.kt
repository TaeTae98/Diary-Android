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
import com.google.accompanist.systemuicontroller.rememberSystemUiController

object DiaryTheme {
    val primaryColor: Color
        @Composable
        get() = Color(0xFF3C5099)

    val onPrimaryColor: Color
        @Composable
        get() = Color(0xFFFFFFFF)

    val backgroundColor: Color
        @Composable
        get() = primaryColor

    val surfaceColor: Color
        @Composable
        get() = Color(0xFFFFFFFF)

    val onSurfaceColor: Color
        @Composable
        get() = Color(0xFF242424)

    val errorColor: Color
        @Composable
        get() = Color.Red

    val colors: Colors
        @Composable
        get() = if (isSystemInDarkTheme()) {
            darkColors()
        } else {
            lightColors(
                primary = primaryColor,
                background = backgroundColor,
                surface = surfaceColor,
                onPrimary = onPrimaryColor,
                onSurface = onSurfaceColor,
            )
        }

    val typography = Typography()
    val shapes = Shapes()
}

@Composable
fun DiaryTheme(
    content: @Composable () -> Unit
) {
    rememberSystemUiController().apply {
        setStatusBarColor(
            color = DiaryTheme.primaryColor
        )
    }

    MaterialTheme(
        colors = DiaryTheme.colors,
        typography = DiaryTheme.typography,
        shapes = DiaryTheme.shapes,
        content = content,
    )
}