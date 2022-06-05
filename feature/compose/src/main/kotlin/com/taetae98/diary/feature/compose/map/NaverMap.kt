package com.taetae98.diary.feature.compose.map

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.NaverMapOptions
import com.taetae98.diary.domain.model.PlaceEntity
import com.taetae98.diary.feature.compose.sideeffct.OnLifecycle
import com.taetae98.diary.feature.compose.variable.canAccessLocation
import com.taetae98.diary.feature.compose.view.NaverMapView

@Composable
fun NaverMap(
    modifier: Modifier = Modifier,
    pin: Collection<PlaceEntity> = emptyList(),
    camera: PlaceEntity? = pin.lastOrNull(),
    onPinClickListener: (PlaceEntity) -> Unit = {},
    onMapClickListener: (PlaceEntity) -> Unit = {},
    isGestureEnable: Boolean = true,
    isLocationButtonEnable: Boolean = canAccessLocation(),
) {
    val (naverMapView, setNaverMapView) = remember { mutableStateOf<NaverMapView?>(null) }
    val bundle = remember { Bundle() }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            NaverMapView(
                context = context,
                options = NaverMapOptions()
                    .camera(
                        camera?.let {
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
            ).also {
                setNaverMapView(it)
            }
        },
        update = { view ->
            view.setPin(pin)
            camera?.let { view.moveCamera(LatLng(it.latitude, it.longitude)) }
            view.setLocationTrackingMode(camera == null)
            view.setGestureEnable(isGestureEnable)
            view.setLocationButtonEnable(isLocationButtonEnable)
        }
    )

    OnLifecycle(naverMapView) {
        when (it) {
            Lifecycle.Event.ON_CREATE -> naverMapView?.onCreate(bundle)
            Lifecycle.Event.ON_START -> naverMapView?.onStart()
            Lifecycle.Event.ON_RESUME -> naverMapView?.onResume()
            Lifecycle.Event.ON_PAUSE -> naverMapView?.onPause()
            Lifecycle.Event.ON_STOP -> naverMapView?.onStop()
            Lifecycle.Event.ON_DESTROY -> naverMapView?.onDestroy()
            else -> Unit
        }
    }
}