package com.taetae98.diary.data.datasource

import androidx.room.Dao
import com.taetae98.diary.data.room.BaseDao
import com.taetae98.diary.domain.model.table.MemoPlaceEntity

@Dao
interface MemoPlaceRoomDataSource : BaseDao<MemoPlaceEntity>