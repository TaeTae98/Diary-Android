package com.taetae98.diary.feature.memo

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.taetae98.diary.feature.common.Parameter

object MemoGraph {
    const val ROUTE = "MemoGraph"
}

fun NavGraphBuilder.memoGraph(
    navController: NavController
) {
    navigation(
        startDestination = MemoScreen.ROUTE,
        route = MemoGraph.ROUTE
    ) {
        composable(
            route = MemoScreen.ROUTE,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = MemoScreen.ROUTE
                }
            )
        ) {
            MemoScreen(navController = navController)
        }

        composable(
            route = MemoEditScreen.ROUTE,
            arguments = listOf(
                navArgument(Parameter.MEMO_ID) {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) {
            MemoEditScreen(navController = navController)
        }
    }
}