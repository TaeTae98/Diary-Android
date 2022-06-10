package com.taetae98.diary.domain.repository

import android.net.Uri
import androidx.paging.PagingData
import com.taetae98.diary.domain.model.file.FileEntity
import java.io.File
import kotlinx.coroutines.flow.Flow

interface FileRepository {
    suspend fun findById(id: Long): FileEntity
    suspend fun insert(entity: FileEntity, uri: Uri): Long
    suspend fun update(entity: FileEntity): Int
    suspend fun delete(entity: FileEntity): Int

    suspend fun containByPath(path: String): Boolean

    fun pagingByFolderIdAndTagIds(folderId: Long?, tagIds: Collection<Long>): Flow<PagingData<FileEntity>>

    fun getFileList(): List<File>
}