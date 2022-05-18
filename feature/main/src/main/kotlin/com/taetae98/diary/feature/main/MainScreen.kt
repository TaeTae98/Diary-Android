package com.taetae98.diary.feature.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.taetae98.diary.feature.memo.MemoGraph
import com.taetae98.diary.feature.memo.memoGraph

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = MemoGraph.ROUTE,
    ) {
        memoGraph(navController = navController)
    }
}