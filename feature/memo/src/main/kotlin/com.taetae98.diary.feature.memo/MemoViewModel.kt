package com.taetae98.diary.feature.memo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taetae98.diary.domain.usecase.memo.DeleteMemoByIdUseCase
import com.taetae98.diary.domain.usecase.memo.PagingMemoByTagIdsUseCase
import com.taetae98.diary.domain.model.MemoRelation
import com.taetae98.diary.domain.usecase.memo.RestoreMemoRelationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MemoViewModel @Inject constructor(
    private val pagingMemoByTagIdsUseCase: PagingMemoByTagIdsUseCase,
    private val deleteMemoByIdUseCase: DeleteMemoByIdUseCase,
    private val restoreMemoRelationUseCase: RestoreMemoRelationUseCase,
) : ViewModel() {
    val event = MutableSharedFlow<MemoEvent>()

    fun getMemoByTagIds(ids: Collection<Int>) = pagingMemoByTagIdsUseCase(ids).getOrElse {
        viewModelScope.launch { event.emit(MemoEvent.Error(it)) }
        emptyFlow()
    }


    fun deleteMemo(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteMemoByIdUseCase(id).onSuccess {
                event.emit(MemoEvent.DeleteMemo(it))
            }.onFailure {
                event.emit(MemoEvent.Error(it))
            }
        }
    }

    fun restore(relation: MemoRelation) {
        viewModelScope.launch(Dispatchers.IO) {
            restoreMemoRelationUseCase(relation).onFailure {
                event.emit(MemoEvent.Error(it))
            }
        }
    }
}