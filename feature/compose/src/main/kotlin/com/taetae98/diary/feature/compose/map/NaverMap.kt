package com.taetae98.diary.feature.compose.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.taetae98.diary.feature.compose.view.NaverMapView

@Composable
fun NaverMap(
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier
            .fillMaxSize(),
        factory = { context ->
            NaverMapView(context)
        },
        update = { view ->

        }
    )
}