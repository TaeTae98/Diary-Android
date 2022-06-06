package com.taetae98.diary.feature.more

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.taetae98.diary.feature.common.DeepLink
import com.taetae98.diary.feature.developer.developerGraph
import com.taetae98.diary.feature.setting.settingGraph

object MoreGraph {
    const val ROUTE = "MoreGraph"
}

fun NavGraphBuilder.moreGraph(
    navController: NavController
) {
    navigation(
        startDestination = DeepLink.More.MORE_URL,
        route = MoreGraph.ROUTE
    ) {
        composable(
            route = DeepLink.More.MORE_URL
        ) {
            MoreScreen(navController = navController)
        }

        settingGraph(navController = navController)
        developerGraph(navController = navController)
    }
}