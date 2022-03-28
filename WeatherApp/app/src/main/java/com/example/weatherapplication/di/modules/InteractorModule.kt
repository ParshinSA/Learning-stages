package com.example.weatherapplication.di.modules

import com.example.weatherapplication.domain.interactors.CityInteractorImpl
import com.example.weatherapplication.domain.interactors.ForecastInteractorImpl
import com.example.weatherapplication.domain.interactors.ReportInteractorImpl
import com.example.weatherapplication.domain.interactors.interactors_interface.CityInteractor
import com.example.weatherapplication.domain.interactors.interactors_interface.ForecastInteractor
import com.example.weatherapplication.domain.interactors.interactors_interface.ReportInteractor
import dagger.Binds
import dagger.Module

@Module
interface InteractorModule {

    @Suppress("FunctionName")
    @Binds
    fun bindCityInteractorImpl_to_CityInteractor(
        interactor: CityInteractorImpl
    ): CityInteractor

    @Suppress("FunctionName")
    @Binds
    fun bindForecastInteractorImpl_to_ForecastInteractor(
        interactor: ForecastInteractorImpl
    ): ForecastInteractor

    @Suppress("FunctionName")
    @Binds
    fun bindReportInteractorImpl_to_ReportInteractor(
        interactor: ReportInteractorImpl
    ): ReportInteractor
}