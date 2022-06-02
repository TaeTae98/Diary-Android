package com.taetae98.diary.feature.common.util

fun Boolean.isFalse() = !this
fun Boolean?.isNullOrFalse() = this == null || isFalse()
fun Boolean?.isTrue() = this == true