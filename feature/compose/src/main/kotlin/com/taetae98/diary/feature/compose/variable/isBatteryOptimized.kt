package com.taetae98.diary.feature.compose.variable

import android.os.PowerManager
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
import com.taetae98.diary.feature.common.isFalse

@Composable
fun isBatteryOptimized(defaultValue: Boolean = false): Boolean {
    val context = LocalContext.current
    val lifecycle by rememberUpdatedState(LocalLifecycleOwner.current.lifecycle)
    val (isBatteryOptimized, setIsBatteryOptimized) = remember { mutableStateOf(defaultValue) }

    DisposableEffect(lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    setIsBatteryOptimized(
                        context.getSystemService(PowerManager::class.java)
                            .isIgnoringBatteryOptimizations(context.packageName).isFalse()
                    )
                }
                else -> Unit
            }
        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    return isBatteryOptimized
}