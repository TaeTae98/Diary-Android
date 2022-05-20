package com.taetae98.diary.feature.location

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

object LocationGraph {
    const val ROUTE = "LocationGraph"
}

fun NavGraphBuilder.locationGraph(
    navController: NavController
) {
    navigation(
        startDestination = LocationScreen.ROUTE,
        route = LocationGraph.ROUTE
    ) {
        composable(
            route = LocationScreen.ROUTE
        ) {
            LocationScreen(navController = navController)
        }
    }
}