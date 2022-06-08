package com.taetae98.diary.data.di

import com.taetae98.diary.data.repository.DeveloperRepositoryImpl
import com.taetae98.diary.data.repository.FileRepositoryImpl
import com.taetae98.diary.data.repository.MemoPlaceRepositoryImpl
import com.taetae98.diary.data.repository.MemoRepositoryImpl
import com.taetae98.diary.data.repository.PlaceRepositoryImpl
import com.taetae98.diary.data.repository.PlaceSearchRepositoryImpl
import com.taetae98.diary.domain.repository.DeveloperRepository
import com.taetae98.diary.domain.repository.FileRepository
import com.taetae98.diary.domain.repository.MemoPlaceRepository
import com.taetae98.diary.domain.repository.MemoRepository
import com.taetae98.diary.domain.repository.PlaceRepository
import com.taetae98.diary.domain.repository.PlaceSearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryBindsViewModelModule {
    @Binds
    @ViewModelScoped
    abstract fun bindsMemoRepository(repository: MemoRepositoryImpl): MemoRepository

    @Binds
    @ViewModelScoped
    abstract fun bindsDeveloperRepository(repository: DeveloperRepositoryImpl): DeveloperRepository

    @Binds
    @ViewModelScoped
    abstract fun bindsPlaceRepository(repository: PlaceRepositoryImpl): PlaceRepository

    @Binds
    @ViewModelScoped
    abstract fun bindsPlaceSearchRepository(repository: PlaceSearchRepositoryImpl): PlaceSearchRepository

    @Binds
    @ViewModelScoped
    abstract fun bindsMemoPlaceRepository(repository: MemoPlaceRepositoryImpl): MemoPlaceRepository

    @Binds
    @ViewModelScoped
    abstract fun bindsFileRepository(repository: FileRepositoryImpl): FileRepository
}