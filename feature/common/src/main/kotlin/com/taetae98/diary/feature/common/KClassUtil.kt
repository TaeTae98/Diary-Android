package com.taetae98.diary.feature.common

import kotlin.reflect.KClass

fun KClass<*>.getDefaultName() = this.qualifiedName ?: "Anonymous"