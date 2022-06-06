package com.taetae98.diary.feature.main

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Article
import androidx.compose.material.icons.rounded.InsertDriveFile
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.ui.graphics.vector.ImageVector
import com.taetae98.diary.feature.file.FileGraph
import com.taetae98.diary.feature.memo.MemoGraph
import com.taetae98.diary.feature.more.MoreGraph
import com.taetae98.diary.feature.place.PlaceGraph
import com.taetae98.diary.feature.resource.StringResource

sealed class MainNavigationItem(
    val route: String,
    @StringRes
    val stringRes: Int,
    val imageVector: ImageVector,
) {
    object Memo : MainNavigationItem(
        route = MemoGraph.ROUTE,
        stringRes = StringResource.memo,
        imageVector = Icons.Rounded.Article
    )

    object Place : MainNavigationItem(
        route = PlaceGraph.ROUTE,
        stringRes = StringResource.place,
        imageVector = Icons.Rounded.LocationOn
    )

    object More : MainNavigationItem(
        route = MoreGraph.ROUTE,
        stringRes = StringResource.more,
        imageVector = Icons.Rounded.MoreHoriz
    )

    object File : MainNavigationItem(
        route = FileGraph.ROUTE,
        stringRes = StringResource.file,
        imageVector = Icons.Rounded.InsertDriveFile
    )
}
