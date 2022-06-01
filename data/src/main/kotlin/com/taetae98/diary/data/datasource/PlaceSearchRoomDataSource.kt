package com.taetae98.diary.data.datasource

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.taetae98.diary.data.room.BaseDao
import com.taetae98.diary.domain.model.PlaceSearchEntity

@Dao
interface PlaceSearchRoomDataSource : BaseDao<PlaceSearchEntity> {
    @Query("SELECT * FROM PlaceSearchEntity WHERE id = :id")
    suspend fun findById(id: Long): PlaceSearchEntity

    @Query("SELECT * FROM PlaceSearchEntity ORDER BY searchedAt DESC")
    fun pagingAll(): PagingSource<Int, PlaceSearchEntity>

    @Query("DELETE FROM PlaceSearchEntity WHERE id = :id")
    suspend fun deleteById(id: Long): Int
}