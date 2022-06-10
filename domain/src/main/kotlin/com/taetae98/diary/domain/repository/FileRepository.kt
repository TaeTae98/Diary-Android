package com.taetae98.diary.domain.repository

import android.net.Uri
import androidx.paging.PagingData
import com.taetae98.diary.domain.model.file.FileEntity
import java.io.File
import kotlinx.coroutines.flow.Flow

interface FileRepository {
    suspend fun findById(id: Long): FileEntity
    suspend fun findByIds(ids: Collection<Long>): List<FileEntity>
    suspend fun findByFolderId(folderId: Long?): List<FileEntity>
    fun pagingByFolderIdAndTagIds(folderId: Long?, tagIds: Collection<Long>): Flow<PagingData<FileEntity>>

    suspend fun insert(entity: FileEntity, uri: Uri): Long

    suspend fun update(entity: FileEntity): Int
    suspend fun updateFolderIdByIds(folderId: Long?, ids: Collection<Long>): Int

    suspend fun delete(entity: FileEntity): Int
    suspend fun deleteById(id: Long): Int

    suspend fun containByPath(path: String): Boolean

    fun explore(entity: FileEntity)
    suspend fun export(parent: File?, entity: FileEntity): Uri
    fun getFileList(): List<File>
}