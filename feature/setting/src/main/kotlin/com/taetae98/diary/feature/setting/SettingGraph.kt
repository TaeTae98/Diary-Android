package com.taetae98.diary.feature.setting

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.taetae98.diary.feature.common.DeepLink

object SettingGraph {
    const val ROUTE = "SettingGraph"

    fun getAction(): String {
        return ROUTE
    }
}

fun NavGraphBuilder.settingGraph(
    navController: NavController
) {
    navigation(
        startDestination = DeepLink.Setting.SETTING_URL,
        route = SettingGraph.ROUTE
    ) {
        composable(
            route = DeepLink.Setting.SETTING_URL,
        ) {
            SettingScreen(navController = navController)
        }
    }
}