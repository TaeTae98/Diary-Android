package com.taetae98.diary.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.taetae98.diary.data.datasource.FolderRoomDataSource
import com.taetae98.diary.data.manager.FileManager
import com.taetae98.diary.domain.model.file.FolderEntity
import com.taetae98.diary.domain.model.file.FolderRelation
import com.taetae98.diary.domain.repository.FolderRepository
import java.io.File
import javax.inject.Inject

class FolderRepositoryImpl @Inject constructor(
    private val fileManager: FileManager,
    private val folderRoomDataSource: FolderRoomDataSource
) : FolderRepository {
    override suspend fun findById(id: Long) = folderRoomDataSource.findById(id) ?: FolderEntity()
    override suspend fun findByIds(ids: Collection<Long>) = folderRoomDataSource.findByIds(ids)

    override suspend fun findByParentId(parentId: Long?) =
        folderRoomDataSource.findByParentId(parentId)

    override suspend fun findRelationById(id: Long) =
        folderRoomDataSource.findRelationById(id) ?: FolderRelation()

    override fun findFlowById(id: Long) = folderRoomDataSource.findFlowById(id)
    override fun pagingByParentId(parentId: Long?) = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 300
        )
    ) {
        if (parentId == null) folderRoomDataSource.pagingByParentIdIsNull()
        else folderRoomDataSource.pagingByParentId(parentId)
    }.flow

    override suspend fun insert(entity: FolderEntity) = folderRoomDataSource.insert(entity)

    override suspend fun update(entity: FolderEntity) = folderRoomDataSource.update(entity)
    override suspend fun updateParentIdByIds(
        parentId: Long?,
        ids: Collection<Long>
    ) = folderRoomDataSource.updateParentIdByIds(parentId = parentId, ids = ids)

    override suspend fun delete(entity: FolderEntity) = folderRoomDataSource.delete(entity)
    override suspend fun deleteById(id: Long) = folderRoomDataSource.deleteById(id)

    override suspend fun export(file: File?, entity: FolderEntity) = fileManager.export(
        parent = file, entity = entity
    )
}