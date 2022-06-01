package com.taetae98.diary.feature.place.screen

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.taetae98.diary.feature.compose.diary.DiaryMap
import com.taetae98.diary.feature.compose.diary.DiaryTopAppBar
import com.taetae98.diary.feature.compose.map.MapType
import com.taetae98.diary.feature.resource.StringResource

object PlaceScreen {
    const val ROUTE = "PlaceScreen"
}

@Composable
fun PlaceScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Scaffold(
        modifier = modifier,
        topBar = { PlaceTopAppBar() }
    ) {
        DiaryMap(
            onNaverMapSearch = { navController.navigate(PlaceSearchScreen.getAction(MapType.NAVER)) }
        )
    }
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