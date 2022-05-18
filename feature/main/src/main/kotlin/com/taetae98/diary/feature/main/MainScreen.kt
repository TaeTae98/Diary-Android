package com.taetae98.diary.feature.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.taetae98.diary.feature.memo.MemoScreen

@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {
    MemoScreen(modifier = modifier)
}