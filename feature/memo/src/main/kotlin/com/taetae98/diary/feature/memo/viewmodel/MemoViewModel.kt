package com.taetae98.diary.feature.memo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.taetae98.diary.domain.model.MemoRelation
import com.taetae98.diary.domain.usecase.memo.DeleteMemoByIdUseCase
import com.taetae98.diary.domain.usecase.memo.PagingMemoByTagIdsUseCase
import com.taetae98.diary.domain.usecase.memo.RestoreMemoRelationUseCase
import com.taetae98.diary.feature.memo.event.MemoEvent
import com.taetae98.diary.feature.memo.model.MemoPreviewUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class MemoViewModel @Inject constructor(
    private val pagingMemoByTagIdsUseCase: PagingMemoByTagIdsUseCase,
    private val deleteMemoByIdUseCase: DeleteMemoByIdUseCase,
    private val restoreMemoRelationUseCase: RestoreMemoRelationUseCase,
) : ViewModel() {
    val event = MutableSharedFlow<MemoEvent>()

    fun getMemoByTagIds(ids: Collection<Long>) =
        pagingMemoByTagIdsUseCase(PagingMemoByTagIdsUseCase.Ids(ids)).getOrElse {
            viewModelScope.launch { event.emit(MemoEvent.Error(it)) }
            emptyFlow()
        }.map { pagingData ->
            pagingData.map {
                MemoPreviewUiState.from(
                    entity = it,
                    onDelete = { delete(it.id) }
                )
            }
        }.cachedIn(viewModelScope)


    private fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteMemoByIdUseCase(DeleteMemoByIdUseCase.Id(id)).onSuccess {
                event.emit(
                    MemoEvent.DeleteMemo(
                        relation = it,
                        onRestore = { restore(it) }
                    )
                )
            }.onFailure {
                event.emit(MemoEvent.Error(it))
            }
        }
    }

    private fun restore(relation: MemoRelation) {
        viewModelScope.launch(Dispatchers.IO) {
            restoreMemoRelationUseCase(relation).onFailure {
                event.emit(MemoEvent.Error(it))
            }
        }
    }
}