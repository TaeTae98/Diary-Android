package com.taetae98.diary.data.converter

import android.net.Uri
import androidx.room.TypeConverter

object UriConverter {
    @TypeConverter
    fun uriToString(uri: Uri) = uri.toString()
    @TypeConverter
    fun stringToUri(string: String) = Uri.parse(string)
}