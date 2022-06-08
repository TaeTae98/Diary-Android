package com.taetae98.diary.data.datasource

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.taetae98.diary.data.room.BaseDao
import com.taetae98.diary.domain.model.place.PlaceEntity
import com.taetae98.diary.domain.model.place.PlaceRelation

@Dao
interface PlaceRoomDataSource : BaseDao<PlaceEntity> {
    @Query("SELECT * FROM PlaceEntity WHERE id = :id")
    suspend fun findById(id: Long): PlaceEntity?

    @Transaction
    @Query("SELECT * FROM PlaceEntity WHERE id = :id")
    suspend fun findRelationById(id: Long): PlaceRelation?

    @Query("DELETE FROM PlaceEntity WHERE id = :id")
    suspend fun deleteById(id: Long): Int

    @Query("SELECT * FROM PlaceEntity ORDER BY updateAt DESC")
    fun pagingByTagIds(): PagingSource<Int, PlaceEntity>
}