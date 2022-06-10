package com.taetae98.diary.data.manager

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import androidx.core.content.FileProvider
import androidx.core.database.getStringOrNull
import com.taetae98.diary.data.R
import com.taetae98.diary.domain.model.file.FileEntity
import com.taetae98.diary.domain.model.file.FolderEntity
import com.taetae98.diary.feature.common.Const
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileFilter
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID
import javax.inject.Inject
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.suspendCancellableCoroutine

class FileManager @Inject constructor(
    @ApplicationContext
    private val context: Context
) {
    fun getFileList() = File(getInternalDirectory()).listFiles(
        FileFilter {
            it.isFile
        }
    )?.toList() ?: emptyList()

    fun explore(entity: FileEntity) {
        Intent.createChooser(
            Intent(Intent.ACTION_VIEW).apply {
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                setDataAndType(
                    FileProvider.getUriForFile(
                        context, context.getString(R.string.diary_file_provider), File(entity.path)
                    ),
                    entity.getMimeType()
                )
            },
            entity.title
        ).also {
            context.startActivity(it.apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) })
        }
    }

    suspend fun export(parent: File?, entity: FileEntity) = suspendCancellableCoroutine<Uri> { continuation ->
        thread {
            context.contentResolver.insert(
                MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                ContentValues().apply {
                    put(MediaStore.MediaColumns.RELATIVE_PATH, "${getExternalDirectory()}${parent?.let { "${File.separator}${it.path}" } ?: ""}")
                    put(MediaStore.MediaColumns.DISPLAY_NAME, entity.getName())
                }
            )?.let { uri ->
                File(entity.path).inputStream().buffered(STREAM_BUFFER_SIZE).use { inputStream ->
                    context.contentResolver.openOutputStream(uri)?.buffered(STREAM_BUFFER_SIZE)?.use { outputStream ->
                        outputStream.write(inputStream)
                    }
                }
                continuation.resume(uri)
            } ?: continuation.resumeWithException(IllegalStateException("ContentResolver is null or crashed."))
        }
    }

    suspend fun export(parent: File?, entity: FolderEntity) = suspendCancellableCoroutine<Uri> { continuation ->
        thread {
            context.contentResolver.insert(
                MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                ContentValues().apply {
                    put(MediaStore.MediaColumns.RELATIVE_PATH, "${getExternalDirectory()}${parent?.let { "${File.separator}${it.path}" } ?: ""}${File.separator}${entity.title}")
                }
            )?.let {
                continuation.resume(it)
            } ?: continuation.resumeWithException(IllegalStateException("ContentResolver is null or crashed."))
        }
    }

    suspend fun write(uri: Uri) = suspendCancellableCoroutine<File> { continuation ->
        thread {
            val displayName = context.contentResolver.query(
                uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null
            )?.use {
                if (it.moveToFirst()) it.getStringOrNull(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))?.substringAfterLast(".")
                else null
            } ?: continuation.resumeWithException(IllegalStateException("Invalid Uri : $uri"))

            File(getInternalDirectory(), "${UUID.randomUUID()}.$displayName").also { file ->
                context.contentResolver.openInputStream(uri)?.buffered(STREAM_BUFFER_SIZE)?.use { inputStream ->
                    file.outputStream().buffered(STREAM_BUFFER_SIZE).use { outputStream ->
                        outputStream.write(inputStream)
                    }
                } ?: continuation.resumeWithException(IllegalStateException("Invalid Uri : $uri"))

                continuation.resume(file)
            }
        }
    }

    private fun getInternalDirectory() = "${context.filesDir.path}${File.separator}fileEntity"
    private fun getExternalDirectory() = "${Environment.DIRECTORY_DOWNLOADS}${File.separator}Diary"


    private fun OutputStream.write(inputStream: InputStream) {
        val buffer = ByteArray(BUFFER_SIZE)
        while (true) {
            val len = inputStream.read(buffer)
            if (len == -1) {
                break
            }

            write(buffer, 0, len)
        }
    }

    companion object {
        private const val STREAM_BUFFER_SIZE = (10 * Const.MB).toInt()
        private const val BUFFER_SIZE = (2 * Const.MB).toInt()
    }
}