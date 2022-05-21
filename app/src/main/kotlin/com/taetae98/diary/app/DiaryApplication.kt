package com.taetae98.diary.app

import android.app.Application
import com.taetae98.diary.domain.repository.ExceptionRepository
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import kotlin.system.exitProcess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltAndroidApp
class DiaryApplication : Application() {
    @Inject
    lateinit var exceptionRepository: ExceptionRepository

    override fun onCreate() {
        super.onCreate()
        initExceptionHandler()
    }

    private fun initExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler { _, throwable ->
            CoroutineScope(Dispatchers.Main).launch {
                exceptionRepository.insert(throwable)
                exitProcess(0)
            }
        }
    }
}