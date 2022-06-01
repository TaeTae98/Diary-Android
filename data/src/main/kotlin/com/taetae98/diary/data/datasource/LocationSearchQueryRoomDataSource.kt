package com.taetae98.diary.data.datasource

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.taetae98.diary.data.room.BaseDao
import com.taetae98.diary.domain.model.LocationSearchQueryEntity

@Dao
interface LocationSearchQueryRoomDataSource : BaseDao<LocationSearchQueryEntity> {
    @Query("SELECT * FROM LocationSearchQueryEntity WHERE id = :id")
    suspend fun findById(id: Int): LocationSearchQueryEntity

    @Query("SELECT * FROM LocationSearchQueryEntity ORDER BY searchedAt DESC")
    fun pagingAll(): PagingSource<Int, LocationSearchQueryEntity>

    @Query("DELETE FROM LocationSearchQueryEntity WHERE id = :id")
    suspend fun deleteById(id: Int): Int
}