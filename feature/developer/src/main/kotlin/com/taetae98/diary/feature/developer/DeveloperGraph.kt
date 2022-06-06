package com.taetae98.diary.feature.developer

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.taetae98.diary.feature.common.DeepLink
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
        startDestination = DeepLink.Developer.DEVELOPER_URL,
        route = DeveloperGraph.ROUTE
    ) {
        composable(
            route = DeepLink.Developer.DEVELOPER_URL
        ) {
            DeveloperScreen(navController = navController)
        }

        composable(
            route = DeepLink.Developer.EXCEPTION_LOG_URL
        ) {
            ExceptionLogScreen(navController = navController)
        }
    }
}