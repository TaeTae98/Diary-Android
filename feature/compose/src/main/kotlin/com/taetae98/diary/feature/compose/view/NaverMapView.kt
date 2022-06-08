package com.taetae98.diary.feature.compose.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.MotionEvent
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.taetae98.diary.domain.model.place.PlaceEntity

@SuppressLint("ViewConstructor")
class NaverMapView(
    context: Context,
    options: NaverMapOptions = NaverMapOptions()
        .indoorEnabled(true)
        .indoorLevelPickerEnabled(true),
    private val onPinClickListener: (PlaceEntity) -> Unit = {},
    onMapClickListener: (PlaceEntity) -> Unit = {},
) : MapView(context, options) {
    private var marker: Collection<Marker> = emptyList()

    init {
        getMapAsync {
            runCatching {
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
                            link = "https://search.naver.com/search.naver?query=${symbol.caption}",
                            latitude = symbol.position.latitude,
                            longitude = symbol.position.longitude
                        )
                    )
                    true
                }
            }
        }
    }

    fun setPin(pin: Collection<PlaceEntity>) = runCatching {
        marker.forEach { it.detach() }
        marker = pin.map { entity ->
            val latLng = LatLng(entity.latitude, entity.longitude)
            Marker(latLng).apply {
                attach()
                setOnClickListener {
                    onPinClickListener(entity)
                    true
                }
            }
        }
    }

    fun setGestureEnable(enable: Boolean) = runCatching {
        getMapAsync {
            it.uiSettings.apply {
                isStopGesturesEnabled = enable
                isRotateGesturesEnabled = enable
                isScrollGesturesEnabled = enable
                isTiltGesturesEnabled = enable
                isZoomGesturesEnabled = enable
            }
        }
    }

    fun setLocationButtonEnable(enable: Boolean) = runCatching {
        getMapAsync {
            it.uiSettings.isLocationButtonEnabled = enable
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> parent.requestDisallowInterceptTouchEvent(true)
            MotionEvent.ACTION_UP -> parent.requestDisallowInterceptTouchEvent(false)
        }

        return super.dispatchTouchEvent(event)
    }

    fun setLocationTrackingMode(isFollow: Boolean) {
        getMapAsync { map ->
            runCatching {
                map.locationTrackingMode = if (isFollow) {
                    LocationTrackingMode.Follow
                } else {
                    LocationTrackingMode.None
                }
            }
        }
    }

    fun moveCamera(latLng: LatLng) {
        getMapAsync {
            runCatching {
                it.moveCamera(
                    CameraUpdate
                        .scrollTo(latLng)
                        .animate(CameraAnimation.Easing, 1000)
                )
            }
        }
    }

    private fun Marker.attach() {
        getMapAsync {
            runCatching {
                map = it

            }
        }
    }

    private fun Marker.detach() = runCatching {
        map = null
    }
}