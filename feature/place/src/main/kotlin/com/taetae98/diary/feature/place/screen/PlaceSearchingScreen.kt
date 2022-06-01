package com.taetae98.diary.feature.place.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.taetae98.diary.feature.common.Const
import com.taetae98.diary.feature.common.Parameter
import com.taetae98.diary.feature.compose.diary.DiaryTextField
import com.taetae98.diary.feature.compose.diary.DiaryTopAppBar
import com.taetae98.diary.feature.compose.diary.DiaryTopAppBarNavigationIcon
import com.taetae98.diary.feature.compose.input.ClearTextField
import com.taetae98.diary.feature.compose.map.MapType
import com.taetae98.diary.feature.compose.modifier.draggable
import com.taetae98.diary.feature.place.compose.PlaceSearchCompose
import com.taetae98.diary.feature.place.viewmodel.PlaceSearchViewModel
import com.taetae98.diary.feature.theme.DiaryTheme

object PlaceSearchingScreen {
    private const val ROUTE_PATH = "PlaceSearchingScreen"

    const val ROUTE = "$ROUTE_PATH/{${Parameter.MAP_TYPE}}"

    fun getAction(mapType: MapType): String {
        return "$ROUTE_PATH/$mapType"
    }
}

@Composable
fun PlaceSearchingScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    Scaffold(
        modifier = modifier,
        topBar = { PlaceSearchTopBar(navController = navController) }
    ) {
        Content(
            modifier = Modifier.padding(it),
            navController = navController
        )
    }
}

@Composable
private fun PlaceSearchTopBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    placeSearchViewModel: PlaceSearchViewModel = hiltViewModel()
) {
    DiaryTopAppBar(
        modifier = modifier,
        title = {
            val focusRequester = remember { FocusRequester() }

            ClearTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                value = placeSearchViewModel.input.collectAsState().value,
                onValueChange = placeSearchViewModel::setInput,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions {
                    placeSearchViewModel.search()
                    navController.navigate(PlaceSearchScreen.getAction(placeSearchViewModel.getMapType()))
                },
                singleLine = true,
                maxLines = 1,
                colors = DiaryTextField.colors(
                    trailingIconColor = DiaryTheme.onSurfaceColor,
                )
            )

            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
        },
        navigationIcon = {
            DiaryTopAppBarNavigationIcon(navController = navController)
        },
        backgroundColor = DiaryTheme.surfaceColor
    )
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    navController: NavController,
    placeSearchViewModel: PlaceSearchViewModel = hiltViewModel()
) {
    val lazyItems = placeSearchViewModel.paging.collectAsLazyPagingItems()
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(
            items = lazyItems,
            key = { it.id }
        ) {
            PlaceSearchCompose(
                modifier = Modifier
                    .draggable(
                        orientation = Orientation.Horizontal,
                        onDragStopped = { velocity ->
                            if (velocity >= Const.VELOCITY_BOUNDARY) {
                                it?.let { it.onDelete() }
                                true
                            } else {
                                false
                            }
                        }
                    )
                    .clickable {
                        it?.let {
                            placeSearchViewModel.search(it.query)
                            navController.navigate(PlaceSearchScreen.getAction(placeSearchViewModel.getMapType()))
                        }
                    },
                uiState = it
            )
        }
    }
}