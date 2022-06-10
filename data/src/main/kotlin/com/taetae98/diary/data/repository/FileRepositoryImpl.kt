package com.taetae98.diary.data.repository

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.taetae98.diary.data.datasource.FileRoomDataSource
import com.taetae98.diary.data.manager.FileManager
import com.taetae98.diary.domain.model.file.FileEntity
import com.taetae98.diary.domain.repository.FileRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val fileManager: FileManager,
    private val fileRoomDataSource: FileRoomDataSource
) : FileRepository {
    override suspend fun findById(id: Long) = fileRoomDataSource.findById(id) ?: FileEntity()

    override suspend fun insert(entity: FileEntity, uri: Uri) = fileRoomDataSource.insert(
        entity
    ).also { id ->
        val file = fileManager.write(uri).also {
            Toast.makeText(context, it.path, Toast.LENGTH_SHORT).show()
        }
        fileRoomDataSource.update(
            entity.copy(
                id = id,
                path = file.path,
                state = FileEntity.State.NORMAL
            )
        )
    }

    override suspend fun update(entity: FileEntity) = fileRoomDataSource.update(entity)

    override fun pagingByFolderIdAndTagIds(folderId: Long?, tagIds: Collection<Long>) = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 300
        )
    ) {
        if (folderId == null) fileRoomDataSource.pagingByFolderIdIsNull()
        else fileRoomDataSource.pagingByFolderId(folderId)
    }.flow
}