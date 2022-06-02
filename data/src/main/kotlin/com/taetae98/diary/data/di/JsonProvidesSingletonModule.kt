package com.taetae98.diary.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
class JsonProvidesSingletonModule {
    @Provides
    @Singleton
    fun providesJson() = Json {
        ignoreUnknownKeys = true
    }
}