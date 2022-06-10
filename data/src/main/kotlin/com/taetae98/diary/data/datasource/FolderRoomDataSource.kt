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

    @Query("SELECT * FROM FolderEntity WHERE id IN (:ids)")
    suspend fun findByIds(ids: Collection<Long>): List<FolderEntity>

    @Query("SELECT * FROM FolderEntity WHERE parentId = :parentId")
    suspend fun findByParentId(parentId: Long?): List<FolderEntity>

    @Transaction
    @Query("SELECT * FROM FolderEntity WHERE id = :id")
    suspend fun findRelationById(id: Long): FolderRelation?

    @Query("SELECT * FROM FolderEntity WHERE id = :id")
    fun findFlowById(id: Long): Flow<FolderEntity>

    @Query("UPDATE FolderEntity SET parentId = :parentId WHERE id in (:ids)")
    suspend fun updateParentIdByIds(parentId: Long?, ids: Collection<Long>): Int

    @Query("DELETE FROM FolderEntity WHERE id = :id")
    suspend fun deleteById(id: Long): Int

    @Query("SELECT * FROM FolderEntity WHERE parentId = :parentId ORDER BY title, updatedAt DESC")
    fun pagingByParentId(parentId: Long): PagingSource<Int, FolderEntity>

    @Query("SELECT * FROM FolderEntity WHERE parentId IS NULL ORDER BY title, updatedAt DESC")
    fun pagingByParentIdIsNull(): PagingSource<Int, FolderEntity>
}