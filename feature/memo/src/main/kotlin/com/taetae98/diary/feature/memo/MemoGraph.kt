package com.taetae98.diary.feature.memo

import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.taetae98.diary.feature.common.DeepLink
import com.taetae98.diary.feature.common.Parameter
import com.taetae98.diary.feature.memo.screen.MemoDetailScreen
import com.taetae98.diary.feature.memo.screen.MemoFileSelectScreen
import com.taetae98.diary.feature.memo.screen.MemoScreen
import com.taetae98.diary.feature.memo.screen.MemoPlaceSelectScreen

object MemoGraph {
    const val ROUTE = "MemoGraph"
}

fun NavGraphBuilder.memoGraph(
    navController: NavController
) {
    navigation(
        startDestination = DeepLink.Memo.MEMO_URL,
        route = MemoGraph.ROUTE
    ) {
        composable(
            route = DeepLink.Memo.MEMO_URL,
            deepLinks = listOf(
                navDeepLink {
                    action = Intent.ACTION_VIEW
                    uriPattern = DeepLink.Memo.MEMO_URL
                },
                navDeepLink {
                    action = Intent.ACTION_VIEW
                    uriPattern = DeepLink.APP_DEEP_LINK
                }
            )
        ) {
            MemoScreen(navController = navController)
        }

        composable(
            route = DeepLink.Memo.MEMO_DETAIL_URL,
            arguments = listOf(
                navArgument(Parameter.ID) {
                    type = NavType.LongType
                    defaultValue = 0L
                }
            )
        ) {
            MemoDetailScreen(navController = navController)
        }

        composable(
            route = DeepLink.Memo.PLACE_SELECT_URL
        ) {
            MemoPlaceSelectScreen(navController = navController)
        }

        composable(
            route = DeepLink.Memo.FILE_SELECT_URL
        ) {
            MemoFileSelectScreen(navController = navController)
        }
    }
}