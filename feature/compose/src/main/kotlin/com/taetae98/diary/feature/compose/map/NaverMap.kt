package com.taetae98.diary.feature.compose.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.naver.maps.map.LocationTrackingMode
import com.taetae98.diary.domain.model.PlaceEntity
import com.taetae98.diary.feature.compose.variable.canAccessLocation
import com.taetae98.diary.feature.compose.view.NaverMapView

@Composable
fun NaverMap(
    modifier: Modifier = Modifier,
    pin: PlaceEntity? = null,
    onPinClickListener: (PlaceEntity) -> Unit = {},
    onMapClickListener: (PlaceEntity) -> Unit = {},
    isLocationButtonEnabled: Boolean = canAccessLocation(),
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            NaverMapView(
                context = context,
                onPinClickListener = onPinClickListener,
                onMapClickListener = onMapClickListener,
            )
        },
        update = { view ->
            view.setPin(pin)
            view.getMapAsync { map ->
                map.uiSettings.isLocationButtonEnabled = isLocationButtonEnabled
                map.locationTrackingMode = if (pin == null) {
                    LocationTrackingMode.Follow
                } else {
                    LocationTrackingMode.None
                }
            }
        }
    )
}