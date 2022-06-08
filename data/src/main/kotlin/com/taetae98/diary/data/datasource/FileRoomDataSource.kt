package com.taetae98.diary.data.datasource

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.taetae98.diary.data.room.BaseDao
import com.taetae98.diary.domain.model.file.FileEntity

@Dao
interface FileRoomDataSource : BaseDao<FileEntity> {
    @Query("SELECT * FROM FileEntity ORDER BY updatedAt DESC")
    fun findAll(): PagingSource<Int, FileEntity>
}