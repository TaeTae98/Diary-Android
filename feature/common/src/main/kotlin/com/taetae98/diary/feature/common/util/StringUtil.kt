package com.taetae98.diary.feature.common.util

fun String.isImage() = substringAfterLast(".")
    .lowercase()
    .matches(Regex("(jpg)|(jpeg)|(png)|(gif)"))

fun String.isVideo() = substringAfterLast(".")
    .lowercase()
    .matches(Regex("(mp4)|(avi)"))

fun String.isAudio() = substringAfterLast(".")
    .lowercase()
    .matches(Regex("(mp3)"))

fun String.isApk() = substringAfterLast(".")
    .lowercase()
    .matches(Regex("(apk)"))