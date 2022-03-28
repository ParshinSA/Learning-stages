package com.example.weatherapplication.di.modules

import com.example.weatherapplication.data.reporitories.CityRepositoryImpl
import com.example.weatherapplication.data.reporitories.ForecastRepositoryImpl
import com.example.weatherapplication.data.reporitories.ReportRepositoryImpl
import com.example.weatherapplication.domain.repository.CityRepository
import com.example.weatherapplication.domain.repository.ForecastRepository
import com.example.weatherapplication.domain.repository.ReportRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Suppress("FunctionName")
    @Binds
    fun bindForecastRepositoryImpl_to_ForecastRepository(
        repository: ForecastRepositoryImpl
    ): ForecastRepository

    @Suppress("FunctionName")
    @Binds
    fun bindCityRepositoryImpl_to_CityRepository(
        repository: CityRepositoryImpl
    ): CityRepository

    @Suppress("FunctionName")
    @Binds
    fun bindReportRepositoryImpl_to_ReportRepository(
        repository: ReportRepositoryImpl
    ): ReportRepository

}