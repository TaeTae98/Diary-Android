package com.taetae98.diary.feature.place.screen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.taetae98.diary.feature.common.Parameter
import com.taetae98.diary.feature.compose.map.MapType
import com.taetae98.diary.feature.place.viewmodel.PlaceSearchViewModel

object PlaceSearchScreen {
    private const val ROUTE_PATH = "PlaceSearchScreen"

    const val ROUTE = "$ROUTE_PATH/{${Parameter.MAP_TYPE}}"

    fun getAction(mapType: MapType): String {
        return "$ROUTE_PATH/${Parameter.MAP_TYPE}=$mapType"
    }
}

@Composable
fun PlaceSearchScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    placeSearchViewModel: PlaceSearchViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = modifier
    ) {
        Row(modifier = Modifier.padding(it)) {
            Text(text = placeSearchViewModel.input.collectAsState().value)
            Text(text = placeSearchViewModel.getMapType().name)
        }
    }
}