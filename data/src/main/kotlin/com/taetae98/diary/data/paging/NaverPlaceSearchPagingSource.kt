package com.taetae98.diary.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.taetae98.diary.data.datasource.NaverPlaceSearchRetrofitDataSource
import com.taetae98.diary.domain.model.PlaceEntity

class NaverPlaceSearchPagingSource(
    private val query: String,
    private val naverPlaceSearchPagingSource: NaverPlaceSearchRetrofitDataSource,
) : PagingSource<Int, PlaceEntity>() {
    override fun getRefreshKey(state: PagingState<Int, PlaceEntity>) = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PlaceEntity> {
        return try {
            val response = naverPlaceSearchPagingSource.search(query = query)
            LoadResult.Page(
                data = response.items.map { it.toEntity() },
                prevKey = null,
                nextKey = null,
                itemsBefore = 0,
                itemsAfter = 0
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}