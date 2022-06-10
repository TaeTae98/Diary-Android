package com.taetae98.diary.data.datasource

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.taetae98.diary.data.room.BaseDao
import com.taetae98.diary.domain.model.file.FileEntity

@Dao
interface FileRoomDataSource : BaseDao<FileEntity> {
    @Query("SELECT * FROM FileEntity WHERE id = :id")
    suspend fun findById(id: Long): FileEntity?

    @Query("SELECT * FROM FileEntity WHERE folderId = :folderId ORDER BY title, updatedAt DESC")
    fun pagingByFolderId(folderId: Long): PagingSource<Int, FileEntity>

    @Query("SELECT * FROM FileEntity WHERE folderId IS NULL ORDER BY title, updatedAt DESC")
    fun pagingByFolderIdIsNull(): PagingSource<Int, FileEntity>
}