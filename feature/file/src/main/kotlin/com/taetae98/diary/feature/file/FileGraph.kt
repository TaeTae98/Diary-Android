package com.taetae98.diary.feature.file

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.taetae98.diary.feature.common.DeepLink

object FileGraph {
    const val ROUTE = "FileGraph"
}

fun NavGraphBuilder.fileGraph(navController: NavController) {
    navigation(
        startDestination = DeepLink.File.FILE_URL,
        route = FileGraph.ROUTE
    ) {
        composable(
            route = DeepLink.File.FILE_URL
        ) {
            FileScreen(navController = navController)
        }
    }
}