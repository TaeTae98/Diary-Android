package com.taetae98.diary.domain

interface MemoRepository {
    suspend fun insert(entity: MemoEntity): Long
}