package com.taetae98.diary.data.di

import com.taetae98.diary.data.repository.DeveloperRepositoryImpl
import com.taetae98.diary.data.repository.MemoRepositoryImpl
import com.taetae98.diary.domain.repository.DeveloperRepository
import com.taetae98.diary.domain.repository.MemoRepository
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
}