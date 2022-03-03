package com.example.weatherapplication.di.modules

import android.content.Context
import com.example.weatherapplication.data.db.custom_cities_db.CustomCitiesDao
import com.example.weatherapplication.data.db.forecast_db.ForecastDao
import com.example.weatherapplication.data.objects.CustomCities
import com.example.weatherapplication.data.repositories.repo_implementation.CustomCitiesRepositoryImpl
import com.example.weatherapplication.data.repositories.repo_implementation.DatabaseRepositoryImpl
import com.example.weatherapplication.data.repositories.repo_implementation.MemoryRepositoryImpl
import com.example.weatherapplication.data.repositories.repo_implementation.RemoteRepositoryImpl
import com.example.weatherapplication.data.repositories.repo_interface.CustomCitiesRepository
import com.example.weatherapplication.data.repositories.repo_interface.DatabaseRepository
import com.example.weatherapplication.data.repositories.repo_interface.MemoryRepository
import com.example.weatherapplication.data.repositories.repo_interface.RemoteRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideRemoteRepositoryImpl(
        context: Context,
        customCities: CustomCities
    ): RemoteRepository {
        return RemoteRepositoryImpl(
            context = context,
            customCities = customCities
        )
    }

    @Provides
    fun provideForecastDbRepositoryImpl(forecastDao: ForecastDao): DatabaseRepository {
        return DatabaseRepositoryImpl(forecastDao)
    }

    @Provides
    fun provideCustomCitiesRepositoryImpl(customCitiesDao: CustomCitiesDao): CustomCitiesRepository {
        return CustomCitiesRepositoryImpl(customCitiesDao)
    }

    @Provides
    fun provideMemoryRepositoryImpl(context: Context): MemoryRepository {
        return MemoryRepositoryImpl(context)
    }
}