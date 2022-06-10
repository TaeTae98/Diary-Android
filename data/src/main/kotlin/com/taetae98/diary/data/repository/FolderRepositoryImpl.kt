package com.taetae98.diary.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.taetae98.diary.data.datasource.FolderRoomDataSource
import com.taetae98.diary.domain.model.file.FolderEntity
import com.taetae98.diary.domain.model.file.FolderRelation
import com.taetae98.diary.domain.repository.FolderRepository
import javax.inject.Inject

class FolderRepositoryImpl @Inject constructor(
    private val folderRoomDataSource: FolderRoomDataSource
) : FolderRepository {
    override suspend fun findById(id: Long) = folderRoomDataSource.findById(id) ?: FolderEntity()
    override suspend fun findRelationById(id: Long) = folderRoomDataSource.findRelationById(id) ?: FolderRelation()
    override fun findFlowById(id: Long) = folderRoomDataSource.findFlowById(id)

    override suspend fun insert(entity: FolderEntity) = folderRoomDataSource.insert(entity)
    override suspend fun update(entity: FolderEntity) = folderRoomDataSource.update(entity)
    override suspend fun delete(entity: FolderEntity) = folderRoomDataSource.delete(entity)
    override suspend fun deleteById(id: Long) = folderRoomDataSource.deleteById(id)

    override fun pagingByParentId(parentId: Long?) = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 300
        )
    ) {
        if (parentId == null) folderRoomDataSource.pagingByParentIdIsNull()
        else folderRoomDataSource.pagingByParentId(parentId)
    }.flow
}