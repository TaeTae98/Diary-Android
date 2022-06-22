package com.taetae98.diary.feature.memo.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Map
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.taetae98.diary.domain.model.place.PlaceEntity
import com.taetae98.diary.feature.common.DeepLink
import com.taetae98.diary.feature.common.Parameter
import com.taetae98.diary.feature.common.util.getResult
import com.taetae98.diary.feature.common.util.removeResult
import com.taetae98.diary.feature.common.util.setResult
import com.taetae98.diary.feature.compose.diary.DiaryMap
import com.taetae98.diary.feature.compose.diary.DiaryTopAppBar
import com.taetae98.diary.feature.compose.diary.DiaryTopAppBarNavigationIcon
import com.taetae98.diary.feature.compose.sideeffct.OnLifecycle
import com.taetae98.diary.feature.memo.viewmodel.PlaceSelectViewModel
import com.taetae98.diary.feature.resource.StringResource

@Composable
fun MemoPlaceSelectScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Scaffold(
        modifier = modifier,
        topBar = { PlaceSelectTopAppBar(navController = navController) },
        floatingActionButton = { MapButton(navController = navController) }
    ) {
        Content(
            modifier = Modifier.padding(it),
            navController = navController
        )
    }

    CollectEvent(navController = navController)
}

@Composable
private fun CollectEvent(
    navController: NavController
) {
    val entity = navController.getResult<PlaceEntity>(Parameter.PLACE).value

    OnLifecycle {
        when (it) {
            Lifecycle.Event.ON_START -> {
                entity?.let { entity ->
                    navController.setResult(Parameter.PLACE, entity)
                    navController.removeResult(Parameter.PLACE)
                    navController.navigateUp()
                }
            }
            else -> Unit
        }
    }
}

@Composable
private fun PlaceSelectTopAppBar(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    DiaryTopAppBar(
        modifier = modifier,
        title = {
            Text(text = stringResource(id = StringResource.place))
        },
        navigationIcon = {
            DiaryTopAppBarNavigationIcon(navController = navController)
        }
    )
}

@Composable
private fun MapButton(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = {
            navController.navigate(DeepLink.Place.getPlaceDetailAction())
        }
    ) {
        Icon(
            imageVector = Icons.Rounded.Map,
            contentDescription = stringResource(id = StringResource.map)
        )
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    navController: NavController,
    placeSelectViewModel: PlaceSelectViewModel = hiltViewModel()
) {
    val lazyPagingItems =
        placeSelectViewModel.pagingByPlaceByTagIds(emptyList()).collectAsLazyPagingItems()
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(
            items = lazyPagingItems,
            key = { it.id }
        ) {
            Card(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .clickable(
                        enabled = it != null,
                        onClickLabel = it?.title,
                    ) {
                        it?.let {
                            navController.setResult(Parameter.PLACE, it)
                            navController.navigateUp()
                        }
                    }
            ) {
                if (it == null) {
                    Loading()
                } else {
                    UiState(entity = it)
                }
            }
        }
    }
}

@Composable
private fun Loading(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun UiState(
    modifier: Modifier = Modifier,
    entity: PlaceEntity
) {
    val (isMapVisible, setMapVisible) = remember { mutableStateOf(false) }

    Column(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1F)
                    .padding(start = 8.dp),
                text = entity.title
            )
            IconButton(onClick = { setMapVisible(isMapVisible.not()) }) {
                if (isMapVisible) {
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowUp,
                        contentDescription = stringResource(id = StringResource.hide)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowDown,
                        contentDescription = stringResource(id = StringResource.show)
                    )
                }
            }
        }

        if (isMapVisible) {
            Divider()
            DiaryMap(
                modifier = Modifier.heightIn(max = 400.dp),
                pin = listOf(entity),
                isGestureEnable = false,
                isLocationButtonEnable = false
            )
        }
    }
}
