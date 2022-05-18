package com.taetae98.diary.data

import com.taetae98.diary.domain.MemoRepository
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
}