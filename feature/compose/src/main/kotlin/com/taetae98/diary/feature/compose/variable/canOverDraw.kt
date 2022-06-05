package com.taetae98.diary.feature.compose.variable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import com.taetae98.diary.feature.common.Permission
import com.taetae98.diary.feature.compose.sideeffct.OnLifecycle

@Composable
fun canDrawOverlays(): Boolean {
    val context = LocalContext.current
    val (canDrawOverlays, setCanDrawOverlays) = remember { mutableStateOf(Permission.canDrawOverlays(context)) }

    OnLifecycle {
        when (it) {
            Lifecycle.Event.ON_START -> setCanDrawOverlays(Permission.canDrawOverlays(context))
            else -> Unit
        }
    }

    return canDrawOverlays
}