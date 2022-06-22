package com.taetae98.diary.domain.model.exception

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.taetae98.diary.feature.common.util.getDefaultName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    indices = [
        Index(
            value = ["type"]
        )
    ]
)
data class ExceptionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val cause: String? = null,
    val stackTrace: String = "",
    val type: String = "",
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable {
    constructor(throwable: Throwable) : this(
        id = 0L,
        cause = throwable.cause?.toString(),
        stackTrace = throwable.stackTraceToString(),
        type = throwable::class.getDefaultName()
    )
}