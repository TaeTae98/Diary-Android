package com.taetae98.diary.feature.compose.variable

import android.provider.Settings
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

@Composable
fun canDrawOverlays(defaultValue: Boolean = false): Boolean {
    val context = LocalContext.current
    val lifecycle by rememberUpdatedState(LocalLifecycleOwner.current.lifecycle)
    val (canDrawOverlays, setCanDrawOverlays) = remember { mutableStateOf(defaultValue) }

    DisposableEffect(lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> setCanDrawOverlays(Settings.canDrawOverlays(context))
                else -> Unit
            }
        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    return canDrawOverlays
}