package com.taetae98.diary.data.datasource

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.taetae98.diary.data.room.BaseDao
import com.taetae98.diary.domain.model.file.FolderEntity
import com.taetae98.diary.domain.model.file.FolderRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface FolderRoomDataSource : BaseDao<FolderEntity> {
    @Query("SELECT * FROM FolderEntity WHERE id = :id")
    suspend fun findById(id: Long): FolderEntity?

    @Transaction
    @Query("SELECT * FROM FolderEntity WHERE id = :id")
    suspend fun findRelationById(id: Long): FolderRelation?

    @Query("SELECT * FROM FolderEntity WHERE id = :id")
    fun findFlowById(id: Long): Flow<FolderEntity>

    @Query("DELETE FROM FolderEntity WHERE id = :id")
    suspend fun deleteById(id: Long): Int

    @Query("SELECT * FROM FolderEntity WHERE parentId = :parentId ORDER BY title, updatedAt DESC")
    fun pagingByParentId(parentId: Long): PagingSource<Int, FolderEntity>

    @Query("SELECT * FROM FolderEntity WHERE parentId IS NULL ORDER BY title, updatedAt DESC")
    fun pagingByParentIdIsNull(): PagingSource<Int, FolderEntity>
}