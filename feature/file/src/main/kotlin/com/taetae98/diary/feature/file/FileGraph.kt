package com.taetae98.diary.feature.file

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.taetae98.diary.feature.common.DeepLink
import com.taetae98.diary.feature.common.Parameter
import com.taetae98.diary.feature.file.screen.FileScreen
import com.taetae98.diary.feature.file.screen.FolderDetailScreen

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

        composable(
            route = DeepLink.File.FOLDER_DETAIL_URL,
            arguments = listOf(
                navArgument(Parameter.ID) {
                    type = NavType.LongType
                    defaultValue = 0L
                },
                navArgument(Parameter.PARENT_ID) {
                    type = NavType.LongType
                    defaultValue = 0L
                }
            )
        ) {
            FolderDetailScreen(navController = navController)
        }
    }
}