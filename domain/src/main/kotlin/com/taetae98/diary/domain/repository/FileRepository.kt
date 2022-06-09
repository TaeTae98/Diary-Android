package com.taetae98.diary.domain.repository

import android.net.Uri
import androidx.paging.PagingData
import com.taetae98.diary.domain.model.file.FileEntity
import kotlinx.coroutines.flow.Flow

interface FileRepository {
    suspend fun insert(entity: FileEntity, uri: Uri): Long
    fun pagingByFolderIdAndTagIds(folderId: Long?, tagIds: Collection<Long>): Flow<PagingData<FileEntity>>
}