package com.example.weatherapplication.app.di.modules

import com.example.weatherapplication.app.framework.RoomCustomCitiesRepository
import com.example.weatherapplication.app.framework.RoomForecastRepository
import com.example.weatherapplication.app.framework.MemoryRepository
import com.example.weatherapplication.app.framework.RemoteRepository
import com.example.weatherapplication.data.data_source.custom_cities.RoomCustomCitiesDataSource
import com.example.weatherapplication.data.data_source.forecast.RoomForecastDataSource
import com.example.weatherapplication.data.data_source.forecast.RemoteForecastDataSource
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Suppress("FunctionName")
    @Binds
    fun bindForecastRemoteRepositoryImpl_to_RemoteRepository(
        remoteRepositoryImpl: RemoteRepository
    ): RemoteForecastDataSource

    @Suppress("FunctionName")
    @Binds
    fun bindForecastDbRepositoryImpl_to_ForecastDbRepository(
        forecastDbRepositoryImpl: RoomForecastRepository
    ): RoomForecastDataSource

    @Suppress("FunctionName")
    @Binds
    fun bindCustomCitiesDbRepositoryImpl_to_CustomCitiesDbRepository(
        customCitiesDbRepositoryImpl: RoomCustomCitiesRepository
    ): RoomCustomCitiesDataSource

    @Suppress("FunctionName")
    @Binds
    fun bindMemoryRepositoryImpl_to_MemoryRepository(
        memoryRepositoryImpl: MemoryRepository
    ): ReportDataSource
}