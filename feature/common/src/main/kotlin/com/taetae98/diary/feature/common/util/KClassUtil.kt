package com.taetae98.diary.feature.common.util

import kotlin.reflect.KClass

fun KClass<*>.getDefaultName() = this.qualifiedName ?: "Anonymous"