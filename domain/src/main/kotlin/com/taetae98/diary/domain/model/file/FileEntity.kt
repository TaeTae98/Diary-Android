package com.taetae98.diary.domain.model.file

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.taetae98.diary.feature.common.util.isAudio
import com.taetae98.diary.feature.common.util.isImage
import com.taetae98.diary.feature.common.util.isVideo
import java.io.File
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
    fun getName() = "$title.${getExtension()}"

    private fun getExtension() = File(path).extension

    fun getMimeType() = when {
        path.isVideo() -> "video/*"
        path.isImage() -> "image/*"
        path.isAudio() -> "audio/*"
        else -> "application/${getExtension()}"
    }

    enum class State {
        WRITING, NORMAL
    }
}