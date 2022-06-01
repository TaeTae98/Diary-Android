package com.taetae98.diary.feature.location.screen

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
import com.taetae98.diary.feature.common.Parameter
import com.taetae98.diary.feature.compose.diary.DiaryTextField
import com.taetae98.diary.feature.compose.diary.DiaryTopAppBar
import com.taetae98.diary.feature.compose.diary.DiaryTopAppBarNavigationIcon
import com.taetae98.diary.feature.compose.input.ClearTextField
import com.taetae98.diary.feature.compose.map.MapType
import com.taetae98.diary.feature.location.compose.LocationSearchQueryCompose
import com.taetae98.diary.feature.location.viewmodel.LocationSearchViewModel
import com.taetae98.diary.feature.theme.DiaryTheme

object LocationSearchScreen {
    private const val ROUTE_PATH = "LocationSearchScreen"

    const val ROUTE = "$ROUTE_PATH/{${Parameter.MAP_TYPE}}"

    fun getAction(mapType: MapType): String {
        return "$ROUTE_PATH/$mapType"
    }
}

@Composable
fun LocationSearchScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    Scaffold(
        modifier = modifier,
        topBar = { LocationSearchTopBar(navController = navController) }
    ) {
        Content(modifier = Modifier.padding(it))
    }
}

@Composable
private fun LocationSearchTopBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    locationSearchViewModel: LocationSearchViewModel = hiltViewModel()
) {
    DiaryTopAppBar(
        modifier = modifier,
        title = {
            val focusRequester = remember { FocusRequester() }

            ClearTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                value = locationSearchViewModel.input.collectAsState().value,
                onValueChange = locationSearchViewModel::setInput,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions {
                    locationSearchViewModel.search()
                },
                singleLine = true,
                maxLines = 1,
                colors = DiaryTextField.colors(
//                    backgroundColor = DiaryTheme.primaryColor,
//                    trailingIconColor = DiaryTheme.onPrimaryColor,
//                    cursorColor = DiaryTheme.onPrimaryColor
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
    locationSearchViewModel: LocationSearchViewModel = hiltViewModel()
) {
    val lazyItems = locationSearchViewModel.paging.collectAsLazyPagingItems()
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(
            items = lazyItems,
            key = { it.id }
        ) {
            LocationSearchQueryCompose(
                uiState = it
            )
        }
    }
}