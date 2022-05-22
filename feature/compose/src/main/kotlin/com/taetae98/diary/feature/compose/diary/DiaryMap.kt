package com.taetae98.diary.feature.compose.diary

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.taetae98.diary.feature.compose.map.NaverMap

@Composable
fun DiaryMap(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier
    ) {
        NaverMap(
            modifier = Modifier.padding(it)
        )
    }
}