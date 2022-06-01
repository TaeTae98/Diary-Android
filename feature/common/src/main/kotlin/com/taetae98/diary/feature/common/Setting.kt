package com.taetae98.diary.feature.common

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

object Setting {
    fun openManageOverlay(context: Context) {
        Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:${context.packageName}")
        ).also {
            context.startActivity(it)
        }
    }

    fun openIgnoreBatteryOptimization(context: Context) {
        Intent(
            Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS,
        ).also {
            context.startActivity(it)
        }
    }

    fun openApplicationDetails(context: Context) {
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:${context.packageName}")
        ).also {
            context.startActivity(it)
        }
    }
}