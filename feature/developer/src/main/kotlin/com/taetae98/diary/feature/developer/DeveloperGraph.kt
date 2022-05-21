package com.taetae98.diary.feature.developer

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.taetae98.diary.feature.developer.screen.DeveloperScreen
import com.taetae98.diary.feature.developer.screen.ExceptionLogScreen

object DeveloperGraph {
    const val ROUTE = "DeveloperGraph"

    fun getAction(): String {
        return ROUTE
    }
}

fun NavGraphBuilder.developerGraph(
    navController: NavController,
) {
    navigation(
        startDestination = DeveloperScreen.ROUTE,
        route = DeveloperGraph.ROUTE
    ) {
        composable(
            route = DeveloperScreen.ROUTE
        ) {
            DeveloperScreen(navController = navController)
        }

        composable(
            route = ExceptionLogScreen.ROUTE
        ) {
            ExceptionLogScreen(navController = navController)
        }
    }
}