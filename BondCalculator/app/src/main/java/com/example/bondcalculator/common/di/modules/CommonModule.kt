package com.example.bondcalculator.common.di.modules

import com.example.bondcalculator.domain.models.download_progress.DownloadProgress
import com.example.bondcalculator.domain.models.download_progress.DownloadProgressImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface CommonModule {

    @Binds
    @Suppress("FunctionName")
    fun bindProgressDataImpl_to_interface(
        downloadProgressImpl: DownloadProgressImpl
    ): DownloadProgress
}