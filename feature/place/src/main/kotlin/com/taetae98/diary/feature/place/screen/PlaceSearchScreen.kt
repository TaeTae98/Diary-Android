package com.taetae98.diary.feature.place.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.taetae98.diary.feature.common.Parameter
import com.taetae98.diary.feature.common.util.setResult
import com.taetae98.diary.feature.compose.diary.DiaryTextField
import com.taetae98.diary.feature.compose.diary.DiaryTopAppBar
import com.taetae98.diary.feature.compose.diary.DiaryTopAppBarNavigationIcon
import com.taetae98.diary.feature.compose.input.ClearTextField
import com.taetae98.diary.feature.place.compose.PlaceCompose
import com.taetae98.diary.feature.place.event.PlaceSearchEvent
import com.taetae98.diary.feature.place.viewmodel.PlaceSearchViewModel
import com.taetae98.diary.feature.theme.DiaryTheme
import kotlinx.coroutines.flow.collect

object PlaceSearchScreen {
    const val ROUTE = "PlaceSearchScreen"

    fun getAction() = ROUTE
}

@Composable
fun PlaceSearchScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    Scaffold(
        modifier = modifier,
        topBar = { PlaceSearchTopBar(navController = navController) }
    ) {
        Content(
            modifier = Modifier.padding(it),
        )
    }

    CollectEvent(navController = navController)
}

@Composable
private fun CollectEvent(
    navController: NavController,
    placeSearchViewModel: PlaceSearchViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        placeSearchViewModel.event.collect {
            when(it) {
                is PlaceSearchEvent.Search -> {
                    navController.setResult(Parameter.PLACE, it.entity)
                    navController.navigateUp()
                }
                else -> Unit
            }
        }
    }
}

@Composable
private fun PlaceSearchTopBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    placeSearchViewModel: PlaceSearchViewModel = hiltViewModel()
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    DiaryTopAppBar(
        modifier = modifier,
        title = {
            ClearTextField(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fillMaxWidth(),
                value = placeSearchViewModel.input.collectAsState().value,
                onValueChange = placeSearchViewModel::setInput,
                singleLine = true,
                keyboardActions = KeyboardActions {
                    focusManager.clearFocus()
                },
                maxLines = 1,
                colors = DiaryTextField.colors(
                    trailingIconColor = DiaryTheme.onSurfaceColor,
                )
            )
        },
        navigationIcon = {
            DiaryTopAppBarNavigationIcon(navController = navController)
        },
        backgroundColor = DiaryTheme.surfaceColor
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    placeSearchViewModel: PlaceSearchViewModel = hiltViewModel()
) {
    val lazyPagingItems = placeSearchViewModel.search(
        placeSearchViewModel.query.collectAsState().value
    ).collectAsLazyPagingItems()

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(
            items = lazyPagingItems,
        ) {
            PlaceCompose(
                uiState = it,
            )
        }
    }
}

