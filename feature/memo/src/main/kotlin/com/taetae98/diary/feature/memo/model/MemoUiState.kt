package com.taetae98.diary.feature.memo.model

import com.taetae98.diary.domain.model.MemoEntity

data class MemoUiState(
    val id: Int,
    val title: String
) {
    companion object {
        fun from(entity: MemoEntity): MemoUiState {
            return MemoUiState(
                id = entity.id,
                title = entity.title
            )
        }
    }
}