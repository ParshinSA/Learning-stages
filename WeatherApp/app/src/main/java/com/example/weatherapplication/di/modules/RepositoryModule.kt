package com.example.weatherapplication.di.modules

import com.example.weatherapplication.data.repositories.repo_implementation.CustomCitiesDbRepositoryImpl
import com.example.weatherapplication.data.repositories.repo_implementation.ForecastDbRepositoryImpl
import com.example.weatherapplication.data.repositories.repo_implementation.MemoryRepositoryImpl
import com.example.weatherapplication.data.repositories.repo_implementation.RemoteRepositoryImpl
import com.example.weatherapplication.data.repositories.repo_interface.CustomCitiesDbRepository
import com.example.weatherapplication.data.repositories.repo_interface.ForecastDbRepository
import com.example.weatherapplication.data.repositories.repo_interface.MemoryRepository
import com.example.weatherapplication.data.repositories.repo_interface.RemoteRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Suppress("FunctionName")
    @Binds
    fun bindForecastRemoteRepositoryImpl_to_RemoteRepository(
        remoteRepositoryImpl: RemoteRepositoryImpl
    ): RemoteRepository

    @Suppress("FunctionName")
    @Binds
    fun bindForecastDbRepositoryImpl_to_ForecastDbRepository(
        forecastDbRepositoryImpl: ForecastDbRepositoryImpl
    ): ForecastDbRepository

    @Suppress("FunctionName")
    @Binds
    fun bindCustomCitiesDbRepositoryImpl_to_CustomCitiesDbRepository(
        customCitiesDbRepositoryImpl: CustomCitiesDbRepositoryImpl
    ): CustomCitiesDbRepository

    @Suppress("FunctionName")
    @Binds
    fun bindMemoryRepositoryImpl_to_MemoryRepository(
        memoryRepositoryImpl: MemoryRepositoryImpl
    ): MemoryRepository
}