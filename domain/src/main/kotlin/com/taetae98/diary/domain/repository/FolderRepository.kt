package com.taetae98.diary.domain.repository

import androidx.paging.PagingData
import com.taetae98.diary.domain.model.file.FolderEntity
import com.taetae98.diary.domain.model.file.FolderRelation
import kotlinx.coroutines.flow.Flow

interface FolderRepository {
    suspend fun findById(id: Long): FolderEntity
    suspend fun findRelationById(id: Long): FolderRelation
    fun findFlowById(id: Long): Flow<FolderEntity>

    suspend fun insert(entity: FolderEntity): Long
    suspend fun update(entity: FolderEntity): Int
    suspend fun delete(entity: FolderEntity): Int
    suspend fun deleteById(id: Long): Int

    fun pagingByParentId(parentId: Long?): Flow<PagingData<FolderEntity>>
}