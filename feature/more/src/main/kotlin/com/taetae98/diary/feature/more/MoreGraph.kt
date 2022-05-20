package com.taetae98.diary.feature.more

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.taetae98.diary.feature.setting.settingGraph

object MoreGraph {
    const val ROUTE = "MoreGraph"
}

fun NavGraphBuilder.moreGraph(
    navController: NavController
) {
    navigation(
        startDestination = MoreScreen.ROUTE,
        route = MoreGraph.ROUTE
    ) {
        composable(
            route = MoreScreen.ROUTE
        ) {
            MoreScreen(navController = navController)
        }

        settingGraph(navController = navController)
    }
}