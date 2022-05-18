package com.taetae98.diary.domain

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [
        Index(value = ["createdAt"])
    ]
)
data class MemoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val password: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)