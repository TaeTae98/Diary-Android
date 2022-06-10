package com.taetae98.diary.app.di

import com.taetae98.diary.app.service.RunOnUnlockServiceImpl
import com.taetae98.diary.domain.service.RunOnUnlockService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class ServiceBindsViewModelModule {
    @Binds
    @ViewModelScoped
    abstract fun bindsRunOnUnlockService(service: RunOnUnlockServiceImpl): RunOnUnlockService
}