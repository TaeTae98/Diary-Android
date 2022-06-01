package com.taetae98.diary.feature.main

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.taetae98.diary.feature.common.isTrue
import com.taetae98.diary.feature.memo.MemoGraph
import com.taetae98.diary.feature.memo.memoGraph
import com.taetae98.diary.feature.memo.screen.MemoScreen
import com.taetae98.diary.feature.more.MoreScreen
import com.taetae98.diary.feature.more.moreGraph
import com.taetae98.diary.feature.place.placeGraph
import com.taetae98.diary.feature.place.screen.PlaceScreen
import com.taetae98.diary.feature.theme.DiaryTheme

object MainScreen {
    const val ROUTE = "MainScreen"
}


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            MainBottomNavigation(navController = navController)
        }
    ) {
        MainNavHost(
            modifier = Modifier.padding(it),
            navController = navController
        )
    }
}

@Composable
private fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = MemoGraph.ROUTE,
        route = MainScreen.ROUTE
    ) {
        memoGraph(navController = navController)
        placeGraph(navController = navController)
        moreGraph(navController = navController)
    }
}

@Composable
private fun MainBottomNavigation(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val items = listOf(
        MainNavigationItem.Memo,
        MainNavigationItem.Place,
        MainNavigationItem.More
    )

    val navigationVisibleRoute = listOf(MemoScreen.ROUTE, PlaceScreen.ROUTE, MoreScreen.ROUTE)

    val backStackEntry by navController.currentBackStackEntryAsState()
    if (backStackEntry?.destination?.route in navigationVisibleRoute) {
        BottomNavigation(
            modifier = modifier,
            backgroundColor = DiaryTheme.surfaceColor,
        ) {
            items.forEach {
                BottomNavigationItem(
                    item = it,
                    navController = navController
                )
            }
        }
    }
}

@Composable
private fun RowScope.BottomNavigationItem(
    modifier: Modifier = Modifier,
    item: MainNavigationItem,
    navController: NavController
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    BottomNavigationItem(
        modifier = modifier,
        selected = backStackEntry?.destination?.hierarchy?.any { it.route == item.route }.isTrue(),
        onClick = {
            navController.navigate(item.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }

                restoreState = true
                launchSingleTop = true
            }
        },
        icon = {
            Icon(
                imageVector = item.imageVector,
                contentDescription = stringResource(id = item.stringRes)
            )
        },
        label = {
            Text(text = stringResource(id = item.stringRes))
        },
        alwaysShowLabel = false
    )
}