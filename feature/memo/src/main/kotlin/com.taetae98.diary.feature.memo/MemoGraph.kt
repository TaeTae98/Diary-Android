package com.taetae98.diary.feature.memo

import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.taetae98.diary.feature.common.Const
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
                    action = Intent.ACTION_VIEW
                    uriPattern = MemoScreen.ROUTE
                },
                navDeepLink {
                    action = Intent.ACTION_VIEW
                    uriPattern = Const.MAIN_APP_DEEP_LINK
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