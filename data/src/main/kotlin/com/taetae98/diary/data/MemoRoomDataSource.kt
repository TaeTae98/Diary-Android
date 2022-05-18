package com.taetae98.diary.data

import androidx.room.Dao
import com.taetae98.diary.domain.MemoEntity
import com.taetae98.diary.feature.base.BaseDao

@Dao
interface MemoRoomDataSource : BaseDao<MemoEntity>