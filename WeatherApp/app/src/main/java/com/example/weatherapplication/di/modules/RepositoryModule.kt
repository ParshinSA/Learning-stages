package com.example.weatherapplication.di.modules

import android.content.Context
import androidx.work.WorkManager
import com.example.weatherapplication.data.db.custom_cities_db.CustomCitiesDao
import com.example.weatherapplication.data.db.forecast_db.ForecastDao
import com.example.weatherapplication.data.objects.CustomCities
import com.example.weatherapplication.data.repositories.repo_implementation.CustomCitiesDbRepositoryImpl
import com.example.weatherapplication.data.repositories.repo_implementation.ForecastDbRepositoryImpl
import com.example.weatherapplication.data.repositories.repo_implementation.MemoryRepositoryImpl
import com.example.weatherapplication.data.repositories.repo_implementation.RemoteRepositoryImpl
import com.example.weatherapplication.data.repositories.repo_interface.CustomCitiesDbRepository
import com.example.weatherapplication.data.repositories.repo_interface.ForecastDbRepository
import com.example.weatherapplication.data.repositories.repo_interface.MemoryRepository
import com.example.weatherapplication.data.repositories.repo_interface.RemoteRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideRemoteRepositoryImpl(
        workManager: WorkManager,
        customCities: CustomCities
    ): RemoteRepository {
        return RemoteRepositoryImpl(
            workManager = workManager,
            customCities = customCities
        )
    }

    @Provides
    fun provideForecastDbRepositoryImpl(forecastDao: ForecastDao): ForecastDbRepository {
        return ForecastDbRepositoryImpl(forecastDao)
    }

    @Provides
    fun provideCustomCitiesDbRepositoryImpl(customCitiesDao: CustomCitiesDao): CustomCitiesDbRepository {
        return CustomCitiesDbRepositoryImpl(customCitiesDao)
    }

    @Provides
    fun provideMemoryRepositoryImpl(context: Context): MemoryRepository {
        return MemoryRepositoryImpl(context)
    }
}