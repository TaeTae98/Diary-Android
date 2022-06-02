package com.taetae98.diary.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.taetae98.diary.data.datasource.NaverPlaceSearchRetrofitDataSource
import com.taetae98.diary.data.paging.NaverPlaceSearchPagingSource
import com.taetae98.diary.domain.repository.PlaceSearchRepository
import javax.inject.Inject

class PlaceSearchRepositoryImpl @Inject constructor(
    private val naverPlaceSearchPagingSource: NaverPlaceSearchRetrofitDataSource
) : PlaceSearchRepository {
    override fun pagingByMapTypeAndQuery(query: String) = Pager(
        config = PagingConfig(
            pageSize = 5
        ),
        initialKey = 1,
    ) {
        NaverPlaceSearchPagingSource(
            query = query,
            naverPlaceSearchPagingSource = naverPlaceSearchPagingSource
        )
    }.flow
}