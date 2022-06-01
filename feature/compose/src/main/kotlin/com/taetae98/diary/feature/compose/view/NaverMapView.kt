package com.taetae98.diary.feature.compose.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.util.FusedLocationSource

@SuppressLint("ViewConstructor")
class NaverMapView(
    context: Context,
    options: NaverMapOptions = NaverMapOptions()
        .indoorEnabled(true)
) : MapView(context, options) {
    init {
        getMapAsync {
            it.locationSource = FusedLocationSource(context as Activity, 0)
        }
    }
}