package com.taetae98.diary.feature.compose.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.UiSettings
import com.taetae98.diary.domain.model.PlaceEntity
import com.taetae98.diary.feature.compose.variable.canAccessLocation
import com.taetae98.diary.feature.compose.view.NaverMapView

@Composable
fun NaverMap(
    modifier: Modifier = Modifier,
    pin: PlaceEntity? = null,
    onPinClickListener: (PlaceEntity) -> Unit = {},
    onMapClickListener: (PlaceEntity) -> Unit = {},
    isGestureEnable: Boolean = true,
    isLocationButtonEnable: Boolean = canAccessLocation(),
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            NaverMapView(
                context = context,
                options = NaverMapOptions()
                    .camera(
                        pin?.let {
                            CameraPosition(
                                LatLng(it.latitude, it.longitude),
                                14.0
                            )
                        }
                    )
                    .indoorEnabled(true)
                    .indoorLevelPickerEnabled(true),
                onPinClickListener = onPinClickListener,
                onMapClickListener = onMapClickListener,
            )
        },
        update = { view ->
            view.setPin(pin)
            view.getMapAsync { map ->
                map.uiSettings.init(
                    isGestureEnable = isGestureEnable,
                    isLocationButtonEnabled = isLocationButtonEnable
                )
                map.locationTrackingMode = if (pin == null) {
                    LocationTrackingMode.Follow
                } else {
                    LocationTrackingMode.None
                }
            }
        }
    )
}

private fun UiSettings.init(isGestureEnable: Boolean, isLocationButtonEnabled: Boolean) {
    isStopGesturesEnabled = isGestureEnable
    isRotateGesturesEnabled = isGestureEnable
    isScrollGesturesEnabled = isGestureEnable
    isTiltGesturesEnabled = isGestureEnable
    isZoomGesturesEnabled = isGestureEnable
    this.isLocationButtonEnabled = isLocationButtonEnabled
}