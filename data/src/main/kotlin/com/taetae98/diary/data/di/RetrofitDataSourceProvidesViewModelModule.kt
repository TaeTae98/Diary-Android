package com.taetae98.diary.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.taetae98.diary.data.datasource.NaverPlaceSearchRetrofitDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class RetrofitDataSourceProvidesViewModelModule {
    @Provides
    @ViewModelScoped
    fun providesNaverPlaceSearchRetrofitDataSource(json: Json): NaverPlaceSearchRetrofitDataSource {
        return Retrofit.Builder()
            .baseUrl("https://openapi.naver.com/v1/search/")
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create(NaverPlaceSearchRetrofitDataSource::class.java)
    }
}