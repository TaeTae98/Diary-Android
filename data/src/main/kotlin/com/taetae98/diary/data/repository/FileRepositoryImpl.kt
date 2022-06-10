package com.taetae98.diary.data.repository

import android.net.Uri
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.taetae98.diary.data.datasource.FileRoomDataSource
import com.taetae98.diary.data.manager.FileManager
import com.taetae98.diary.domain.model.file.FileEntity
import com.taetae98.diary.domain.repository.FileRepository
import java.io.File
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    private val fileManager: FileManager,
    private val fileRoomDataSource: FileRoomDataSource
) : FileRepository {
    override suspend fun findById(id: Long) = fileRoomDataSource.findById(id) ?: FileEntity()
    override suspend fun findByIds(ids: Collection<Long>) = fileRoomDataSource.findByIds(ids)
    override suspend fun findByFolderId(folderId: Long?) =
        fileRoomDataSource.findByFolderId(folderId)

    override fun pagingByFolderIdAndTagIds(folderId: Long?, tagIds: Collection<Long>) = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 300
        )
    ) {
        if (folderId == null) fileRoomDataSource.pagingByFolderIdIsNull()
        else fileRoomDataSource.pagingByFolderId(folderId)
    }.flow

    override suspend fun insert(entity: FileEntity, uri: Uri) = fileRoomDataSource.insert(
        entity
    ).also { id ->
        val file = fileManager.write(uri)
        fileRoomDataSource.update(
            entity.copy(
                id = id,
                path = file.path,
                state = FileEntity.State.NORMAL
            )
        )
    }

    override suspend fun update(entity: FileEntity) = fileRoomDataSource.update(entity)
    override suspend fun updateFolderIdByIds(
        folderId: Long?,
        ids: Collection<Long>
    ) = fileRoomDataSource.updateFolderIdByIds(folderId = folderId, ids = ids)

    override suspend fun delete(entity: FileEntity) = fileRoomDataSource.delete(entity).also {
        File(entity.path).deleteRecursively()
    }

    override suspend fun deleteById(id: Long) = fileRoomDataSource.findById(id)?.let {
        delete(it)
    } ?: 0

    override suspend fun containByPath(path: String) = fileRoomDataSource.containByPath(path)

    override fun getFileList() = fileManager.getFileList()
    override fun explore(entity: FileEntity) = fileManager.explore(entity)
    override suspend fun export(parent: File?, entity: FileEntity) = fileManager.export(
        parent = parent, entity = entity
    )
}