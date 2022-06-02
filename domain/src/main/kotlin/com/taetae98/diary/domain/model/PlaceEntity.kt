package com.taetae98.diary.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlaceEntity(
    val title: String = "",
    val address: String = "",
    val link: String? = null,
    val description: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
) : Parcelable