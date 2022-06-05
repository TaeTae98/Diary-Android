package com.taetae98.diary.feature.compose.variable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import com.taetae98.diary.feature.common.Permission
import com.taetae98.diary.feature.compose.sideeffct.OnLifecycle

@Composable
fun canAccessFineLocation(): Boolean {
    val context = LocalContext.current
    val (canAccessFineLocation, setCanAccessFineLocation) = remember { mutableStateOf(Permission.canAccessFineLocation(context)) }

    OnLifecycle {
        when (it) {
            Lifecycle.Event.ON_START -> setCanAccessFineLocation(Permission.canAccessFineLocation(context))
            else -> Unit
        }
    }

    return canAccessFineLocation
}