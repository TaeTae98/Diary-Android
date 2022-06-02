package com.taetae98.diary.feature.place.screen

import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.taetae98.diary.domain.model.PlaceEntity
import com.taetae98.diary.feature.common.Parameter
import com.taetae98.diary.feature.common.util.getResult
import com.taetae98.diary.feature.common.util.removeResult
import com.taetae98.diary.feature.compose.diary.DiaryMap
import com.taetae98.diary.feature.compose.diary.DiaryTopAppBar
import com.taetae98.diary.feature.place.viewmodel.PlaceViewModel
import com.taetae98.diary.feature.resource.StringResource

object PlaceScreen {
    const val ROUTE = "PlaceScreen"
}

@Composable
fun PlaceScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    placeViewModel: PlaceViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = modifier,
        topBar = { PlaceTopAppBar() },
    ) {
        DiaryMap(
            pin = placeViewModel.pin.collectAsState().value,
            onPinClickListener = {
                Log.d("PASS", it.toString())
            },
            onMapClickListener = placeViewModel::setPin,
            onSearch = {
                navController.navigate(PlaceSearchScreen.getAction())
            }
        )
    }

    CollectEvent(navController = navController)
}

@Composable
private fun PlaceTopAppBar(
    modifier: Modifier = Modifier,
) {
    DiaryTopAppBar(
        modifier = modifier,
        title = { Text(text = stringResource(id = StringResource.place)) }
    )
}

@Composable
private fun CollectEvent(
    navController: NavController,
    placeViewModel: PlaceViewModel = hiltViewModel(),
) {
    navController.getResult<PlaceEntity>(Parameter.PLACE_SEARCH_ENTITY).value?.let {
        placeViewModel.setPin(it)
        navController.removeResult(Parameter.PLACE_SEARCH_ENTITY)
    }
}