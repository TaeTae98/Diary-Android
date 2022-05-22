package com.taetae98.diary.feature.compose.view

import android.annotation.SuppressLint
import android.content.Context
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMapOptions

@SuppressLint("ViewConstructor")
class NaverMapView(
    context: Context,
    options: NaverMapOptions = NaverMapOptions()
        .indoorEnabled(true)
        .locationButtonEnabled(true)
) : MapView(context, options)