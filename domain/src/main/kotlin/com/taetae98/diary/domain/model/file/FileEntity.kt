package com.taetae98.diary.domain.model.file

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    indices = [
        Index(value = ["path"], unique = true)
    ],
    foreignKeys = [
        ForeignKey(
            entity = FolderEntity::class,
            parentColumns = ["id"],
            childColumns = ["folderId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class  FileEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val folderId: Long? = null,
    val title: String = "",
    val description: String = "",
    val password: String? = null,
    val path: String = "",
    val state: State = State.WRITING,
    val updatedAt: Long = System.currentTimeMillis()
) : Parcelable {
    enum class State {
        WRITING, NORMAL
    }

    fun isImage() = path
        .substringAfterLast(".")
        .lowercase()
        .matches(Regex("(jpg)|(jpeg)|(png)|(gif)"))

    fun isVideo() = path
        .substringAfterLast(".")
        .lowercase()
        .matches(Regex("(mp4)|(avi)"))

    fun isAudio() = path
        .substringAfterLast(".")
        .lowercase()
        .matches(Regex("(mp3)"))

    fun isApk() = path
        .substringAfterLast(".")
        .lowercase()
        .matches(Regex("(apk)"))
}