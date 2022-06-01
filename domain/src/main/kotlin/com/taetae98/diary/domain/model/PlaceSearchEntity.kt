package com.taetae98.diary.domain.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [
        Index(
            value = ["query"],
            unique = true
        )
    ]
)
data class PlaceSearchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val query: String = "",
    val searchedAt: Long = System.currentTimeMillis()
)