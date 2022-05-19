package com.taetae98.diary.feature.memo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taetae98.diary.domain.MemoDeleteByIdUseCase
import com.taetae98.diary.domain.MemoPagingByTagIdsUseCase
import com.taetae98.diary.domain.MemoRelation
import com.taetae98.diary.domain.MemoRelationRestoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MemoViewModel @Inject constructor(
    private val memoPagingByTagIdsUseCase: MemoPagingByTagIdsUseCase,
    private val memoDeleteByIdUseCase: MemoDeleteByIdUseCase,
    private val memoRelationRestoreUseCase: MemoRelationRestoreUseCase,
) : ViewModel() {
    val event = MutableSharedFlow<MemoEvent>()

    fun getMemoByTagIds(ids: Collection<Int>) = memoPagingByTagIdsUseCase(ids).getOrElse {
        viewModelScope.launch { event.emit(MemoEvent.Error(it)) }
        emptyFlow()
    }


    fun deleteMemo(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            memoDeleteByIdUseCase(id).onSuccess {
                event.emit(MemoEvent.DeleteMemo(it))
            }.onFailure {
                event.emit(MemoEvent.Error(it))
            }
        }
    }

    fun restore(relation: MemoRelation) {
        viewModelScope.launch(Dispatchers.IO) {
            memoRelationRestoreUseCase(relation).onFailure {
                event.emit(MemoEvent.Error(it))
            }
        }
    }
}