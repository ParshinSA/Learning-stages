package com.example.weatherapplication.di.modules

import com.example.weatherapplication.services.UpdateForecastService
import dagger.Module

@Module
class ServiceModule {

    fun provideUpdateForecastService(): UpdateForecastService {
        return UpdateForecastService()
    }
}