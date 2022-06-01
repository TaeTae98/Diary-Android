package com.taetae98.diary.feature.place

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.taetae98.diary.feature.common.Parameter
import com.taetae98.diary.feature.compose.map.MapType
import com.taetae98.diary.feature.place.screen.PlaceScreen
import com.taetae98.diary.feature.place.screen.PlaceSearchScreen

object PlaceGraph {
    const val ROUTE = "PlaceGraph"
}

fun NavGraphBuilder.placeGraph(
    navController: NavController
) {
    navigation(
        startDestination = PlaceScreen.ROUTE,
        route = PlaceGraph.ROUTE
    ) {
        composable(
            route = PlaceScreen.ROUTE
        ) {
            PlaceScreen(navController = navController)
        }

        composable(
            route = PlaceSearchScreen.ROUTE,
            arguments = listOf(
                navArgument(
                    name = Parameter.MAP_TYPE
                ) {
                    type = NavType.EnumType(MapType::class.java)
                    defaultValue = MapType.NAVER
                }
            )
        ) {
            PlaceSearchScreen(navController = navController)
        }
    }
}