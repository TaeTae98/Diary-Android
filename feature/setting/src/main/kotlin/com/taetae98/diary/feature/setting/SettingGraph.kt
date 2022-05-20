package com.taetae98.diary.feature.setting

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

object SettingGraph {
    const val ROUTE = "SettingGraph"
}

fun NavGraphBuilder.settingGraph(
    navController: NavController
) {
    navigation(
        startDestination = SettingScreen.ROUTE,
        route = SettingGraph.ROUTE
    ) {
        composable(
            route = SettingScreen.ROUTE
        ) {
            SettingScreen(navController = navController)
        }
    }
}