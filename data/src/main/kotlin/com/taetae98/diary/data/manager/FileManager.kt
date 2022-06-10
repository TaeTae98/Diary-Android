package com.taetae98.diary.data.manager

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.core.database.getStringOrNull
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import com.taetae98.diary.feature.common.Const
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileFilter
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID
import javax.inject.Inject
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext

class FileManager @Inject constructor(
    @ApplicationContext
    private val context: Context
) {
    fun getFileList() = File(context.getFileDirectory()).listFiles(
        FileFilter {
            it.isFile
        }
    )?.toList() ?: emptyList()

    suspend fun write(uri: Uri) = context.buildFile(uri).also { file ->
        file.write(uri)
    }

    suspend fun securityWrite(uri: Uri) = context.buildFile(uri).also { file ->
        file.securityWrite(uri)
    }

    private fun Context.getFileDirectory() = "${filesDir.path}${File.separator}fileEntity"

    private suspend fun Context.buildFile(uri: Uri) = File(
        getFileDirectory(),
        "${UUID.randomUUID()}.${getDisplayName(uri).substringAfterLast(".")}"
    ).apply {
        parentFile?.mkdirs()
    }

    private suspend fun Context.getDisplayName(uri: Uri) = withContext(Dispatchers.IO) {
        contentResolver.query(
            uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null
        )?.use {
            if (it.moveToFirst()) it.getStringOrNull(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            else null
        } ?: throw IllegalStateException("Invalid uri : $uri")
    }

    private suspend fun File.write(uri: Uri) = suspendCancellableCoroutine<Unit> { continuation ->
        thread {
            context.contentResolver.openInputStream(uri)?.buffered(STREAM_BUFFER_SIZE)
                ?.use { inputStream ->
                    FileOutputStream(this).buffered(STREAM_BUFFER_SIZE).use { outputStream ->
                        inputStream.write(outputStream)
                    }
                } ?: throw IllegalStateException("Invalid uri : $uri")

            continuation.resume(Unit)
        }
    }

    private suspend fun File.securityWrite(uri: Uri) =
        suspendCancellableCoroutine<Unit> { continuation ->
            thread {
                context.contentResolver.openInputStream(uri)?.buffered(STREAM_BUFFER_SIZE)
                    ?.use { inputStream ->
                        toEncryptedFile().openFileOutput().buffered(STREAM_BUFFER_SIZE)
                            .use { outputStream ->
                                inputStream.write(outputStream)
                            }
                    } ?: throw IllegalStateException("Invalid uri : $uri")

                continuation.resume(Unit)
            }
        }


    private fun File.toEncryptedFile() = EncryptedFile.Builder(
        this,
        context,
        KEY_ALIAS,
        EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
    ).build()

    private fun InputStream.write(outputStream: OutputStream) {
        val buffer = ByteArray(BUFFER_SIZE)
        while (true) {
            val len = read(buffer)
            if (len == -1) {
                break
            }

            outputStream.write(buffer, 0, len)
        }
    }

    companion object {
        private const val STREAM_BUFFER_SIZE = (10 * Const.MB).toInt()
        private const val BUFFER_SIZE = (2 * Const.MB).toInt()

        private val KEY_ALIAS = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    }
}