package com.taetae98.diary.app.di

import com.taetae98.diary.app.call.RunOnUnlockCallImpl
import com.taetae98.diary.domain.call.RunOnUnlockCall
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class CallBindsViewModelModule {
    @Binds
    @ViewModelScoped
    abstract fun bindsRunOnUnlockService(service: RunOnUnlockCallImpl): RunOnUnlockCall
}