package com.taetae98.diary.feature.common.util

fun String.isImage() = substringAfterLast(".")
    .lowercase()
    .matches(Regex("(jpg)|(jpeg)|(png)|(gif)|(bmp)"))

fun String.isVideo() = substringAfterLast(".")
    .lowercase()
    .matches(Regex("(mp4)|(avi)|(mpeg)"))

fun String.isAudio() = substringAfterLast(".")
    .lowercase()
    .matches(Regex("(mp3)|(wav)|(ogg)|(mid)|(midi)|(amr)"))

fun String.isApk() = substringAfterLast(".")
    .lowercase()
    .matches(Regex("(apk)"))