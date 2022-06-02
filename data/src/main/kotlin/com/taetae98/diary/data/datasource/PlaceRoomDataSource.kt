package com.taetae98.diary.data.datasource

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.taetae98.diary.data.room.BaseDao
import com.taetae98.diary.domain.model.PlaceEntity

@Dao
interface PlaceRoomDataSource : BaseDao<PlaceEntity> {
    @Query("SELECT * FROM PlaceEntity WHERE id = :id")
    suspend fun findById(id: Long): PlaceEntity?

    @Query("SELECT * FROM PlaceEntity ORDER BY updateAt DESC")
    fun pagingByTagIds(): PagingSource<Int, PlaceEntity>
}