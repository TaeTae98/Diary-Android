package com.taetae98.diary.domain.model.place

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    indices = [
        Index(
            value = ["title"]
        )
    ]
)
data class PlaceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String = "",
    val address: String = "",
    val link: String? = null,
    val description: String = "",
    val password: String? = null,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val updateAt: Long = System.currentTimeMillis()
) : Parcelable