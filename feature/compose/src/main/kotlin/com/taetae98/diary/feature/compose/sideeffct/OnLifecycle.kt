package com.taetae98.diary.feature.compose.sideeffct

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

@Composable
fun OnLifecycle(key: Any? = null, onLifecycle: (Lifecycle.Event) -> Unit) {
    val lifecycle by rememberUpdatedState(LocalLifecycleOwner.current.lifecycle)
    val observer = remember {
        LifecycleEventObserver { _, event ->
            onLifecycle(event)
        }
    }

    DisposableEffect(key1 = key, key2 = lifecycle) {
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}