package com.example.weatherapplication.di.modules

import android.content.Context
import android.util.Log
import androidx.work.WorkManager
import com.example.weatherapplication.data.db.custom_cities_db.CustomCitiesDao
import com.example.weatherapplication.data.db.forecast_db.ForecastDao
import com.example.weatherapplication.data.networking.api.CoordinationApi
import com.example.weatherapplication.data.networking.api.ForecastApi
import com.example.weatherapplication.data.networking.api.HistoryApi
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
import io.reactivex.Observable
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRemoteRepositoryImpl(
        workManager: WorkManager,
        forecastApi: ForecastApi,
        historyApi: HistoryApi,
        coordinationApi: CoordinationApi
    ): RemoteRepository {
        return RemoteRepositoryImpl(
            workManager = workManager,
            forecastApi = forecastApi,
            historyApi = historyApi,
            coordinationApi = coordinationApi
        )
    }

    @Provides
    @Singleton
    fun provideForecastDbRepositoryImpl(forecastDao: ForecastDao): ForecastDbRepository {
        return ForecastDbRepositoryImpl(forecastDao)
    }

    @Provides
    @Singleton
    fun provideCustomCitiesDbRepositoryImpl(customCitiesDao: CustomCitiesDao): CustomCitiesDbRepository {
        return CustomCitiesDbRepositoryImpl(customCitiesDao)
    }

    @Provides
    @Singleton
    fun provideMemoryRepositoryImpl(context: Observable<Context>): MemoryRepository {
        return MemoryRepositoryImpl(context)
    }
}