package com.taetae98.diary.feature.memo

import com.taetae98.diary.domain.MemoRelation

sealed class MemoEvent : MemoEditEvent(){
    data class DeleteMemo(val relation: MemoRelation) : MemoEvent()
    data class Error(val throwable: Throwable) : MemoEvent()
}