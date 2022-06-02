package com.taetae98.diary.data.datasource

import com.taetae98.diary.data.BuildConfig
import com.taetae98.diary.data.response.NaverPlaceSearchResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NaverPlaceSearchRetrofitDataSource {
    @GET("local.json")
    @Headers(
        "X-Naver-Client-Id:${BuildConfig.NAVER_CLIENT_ID}",
        "X-Naver-Client-Secret:${BuildConfig.NAVER_CLIENT_SECRET}",
    )
    suspend fun search(
        @Query("query")
        query: String,
        @Query("display")
        display: Int = 5
    ): NaverPlaceSearchResponse
}