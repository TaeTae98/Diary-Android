package com.taetae98.diary.feature.location

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.taetae98.diary.feature.common.Parameter
import com.taetae98.diary.feature.compose.map.MapType
import com.taetae98.diary.feature.location.screen.LocationScreen
import com.taetae98.diary.feature.location.screen.LocationSearchScreen

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

        composable(
            route = LocationSearchScreen.ROUTE,
            arguments = listOf(
                navArgument(
                    name = Parameter.MAP_TYPE
                ) {
                    type = NavType.EnumType(MapType::class.java)
                    defaultValue = MapType.NAVER
                }
            )
        ) {
            LocationSearchScreen(navController = navController)
        }
    }
}