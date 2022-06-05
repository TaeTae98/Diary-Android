package com.taetae98.diary.feature.memo.viewmodel

import androidx.lifecycle.ViewModel
import com.taetae98.diary.domain.usecase.place.PagingPlaceByTagIdsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.emptyFlow

@HiltViewModel
class PlaceSelectViewModel @Inject constructor(
    private val pagingPlaceByTagIdsUseCase: PagingPlaceByTagIdsUseCase
) : ViewModel() {
    fun pagingByPlaceByTagIds(ids: Collection<Long>) = pagingPlaceByTagIdsUseCase(
        PagingPlaceByTagIdsUseCase.Ids(ids)
    ).getOrElse {
        emptyFlow()
    }
}