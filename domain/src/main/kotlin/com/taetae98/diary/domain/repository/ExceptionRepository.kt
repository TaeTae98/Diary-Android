package com.taetae98.diary.domain.repository

import androidx.paging.PagingData
import com.taetae98.diary.domain.model.ExceptionEntity
import kotlinx.coroutines.flow.Flow

interface ExceptionRepository {
    suspend fun insert(throwable: Throwable): Long
    suspend fun insert(collection: Collection<ExceptionEntity>): LongArray

    suspend fun findAll(): List<ExceptionEntity>
    fun pagingAll(): Flow<PagingData<ExceptionEntity>>

    suspend fun findById(id: Long): ExceptionEntity
    suspend fun deleteById(id: Long): Int
    suspend fun deleteAll(): Int
}