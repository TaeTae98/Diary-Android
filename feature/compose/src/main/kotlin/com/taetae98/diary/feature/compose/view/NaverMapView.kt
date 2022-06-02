package com.taetae98.diary.feature.compose.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.taetae98.diary.domain.model.PlaceEntity

@SuppressLint("ViewConstructor")
class NaverMapView(
    context: Context,
    options: NaverMapOptions = NaverMapOptions()
        .indoorEnabled(true)
        .indoorLevelPickerEnabled(true),
    private val onPinClickListener: (PlaceEntity) -> Unit = {},
    onMapClickListener: (PlaceEntity) -> Unit = {},
) : MapView(context, options) {
    private var pin: Marker? = null

    init {
        getMapAsync {
            it.locationSource = FusedLocationSource(context as Activity, 0)
            it.setOnMapClickListener { _, latLng ->
                onMapClickListener(
                    PlaceEntity(
                        latitude = latLng.latitude,
                        longitude = latLng.longitude
                    )
                )
            }
            it.setOnSymbolClickListener { symbol ->
                onMapClickListener(
                    PlaceEntity(
                        title = symbol.caption,
                        latitude = symbol.position.latitude,
                        longitude = symbol.position.longitude
                    )
                )
                true
            }
        }
    }

    fun setPin(placeEntity: PlaceEntity?) {
        pin?.map = null
        placeEntity?.let { entity ->
            val latLng = LatLng(entity.latitude, entity.longitude)
            Marker(latLng).apply {
                getMapAsync {
                    map = it
                    it.moveCamera(
                        CameraUpdate
                            .scrollTo(latLng)
                            .animate(CameraAnimation.Easing, 1000)
                    )
                }
                setOnClickListener {
                    onPinClickListener(entity)
                    true
                }
            }.also {
                pin = it
            }
        }
    }
}