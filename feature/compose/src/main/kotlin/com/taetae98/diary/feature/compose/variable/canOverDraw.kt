package com.taetae98.diary.feature.compose.variable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.taetae98.diary.feature.common.Permission

@Composable
fun canDrawOverlays(): Boolean {
    val context = LocalContext.current
    val lifecycle by rememberUpdatedState(LocalLifecycleOwner.current.lifecycle)
    val (canDrawOverlays, setCanDrawOverlays) = remember { mutableStateOf(Permission.canDrawOverlays(context)) }
    val observer = remember {
        LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> setCanDrawOverlays(Permission.canDrawOverlays(context))
                else -> Unit
            }
        }
    }

    DisposableEffect(lifecycle) {

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    return canDrawOverlays
}