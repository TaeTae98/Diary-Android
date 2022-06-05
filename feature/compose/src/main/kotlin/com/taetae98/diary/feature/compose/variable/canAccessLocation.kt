package com.taetae98.diary.feature.compose.variable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import com.taetae98.diary.feature.common.Permission
import com.taetae98.diary.feature.compose.sideeffct.OnLifecycle

@Composable
fun canAccessLocation(): Boolean {
    val context = LocalContext.current
    val (canAccessLocation, setCanAccessLocation) = remember { mutableStateOf(Permission.canAccessLocation(context)) }

    OnLifecycle {
        when (it) {
            Lifecycle.Event.ON_START -> setCanAccessLocation(Permission.canAccessLocation(context))
            else -> Unit
        }
    }

    return canAccessLocation
}