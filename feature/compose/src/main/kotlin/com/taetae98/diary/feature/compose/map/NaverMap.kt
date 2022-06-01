package com.taetae98.diary.feature.compose.map

import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.naver.maps.map.LocationTrackingMode
import com.taetae98.diary.feature.compose.variable.canAccessLocation
import com.taetae98.diary.feature.compose.view.NaverMapView
import com.taetae98.diary.feature.resource.StringResource

@Composable
fun NaverMap(
    modifier: Modifier = Modifier,
    onSearch: (() -> Unit)? = null,
    isLocationButtonEnabled: Boolean = canAccessLocation(),
    locationTrackingMode: LocationTrackingMode = if (isLocationButtonEnabled) LocationTrackingMode.Follow else LocationTrackingMode.None
) {
    Scaffold(
        floatingActionButton = { onSearch?.let { SearchButton(onSearch = it) } }
    ) {
        AndroidView(
            modifier = modifier.padding(it),
            factory = { context ->
                NaverMapView(context)
            },
            update = { view ->
                view.getMapAsync { map ->
                    map.uiSettings.isLocationButtonEnabled = isLocationButtonEnabled
                    map.locationTrackingMode = locationTrackingMode
                }
            }
        )
    }
}

@Composable
private fun SearchButton(
    modifier: Modifier = Modifier,
    onSearch: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onSearch
    ) {
        Icon(
            imageVector = Icons.Rounded.Search,
            contentDescription = stringResource(id = StringResource.search)
        )
    }
}