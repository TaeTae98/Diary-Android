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
import com.taetae98.diary.feature.common.util.isFalse
import com.taetae98.diary.feature.compose.sideeffct.OnLifecycle

@Composable
fun isBatteryOptimized(): Boolean {
    val context = LocalContext.current
    val (isBatteryOptimized, setIsBatteryOptimized) = remember {
        mutableStateOf(
            Permission.isIgnoringBatteryOptimizations(context).isFalse()
        )
    }

    OnLifecycle {
        when (it) {
            Lifecycle.Event.ON_START -> {
                setIsBatteryOptimized(
                    Permission.isIgnoringBatteryOptimizations(context).isFalse()
                )
            }
            else -> Unit
        }
    }

    return isBatteryOptimized
}