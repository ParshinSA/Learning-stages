package com.example.weatherapplication.di.modules

import com.example.weatherapplication.data.objects.AppDisposable
import com.example.weatherapplication.data.repositories.repo_interface.ForecastDbRepository
import com.example.weatherapplication.data.repositories.repo_interface.MemoryRepository
import com.example.weatherapplication.data.repositories.repo_interface.RemoteRepository
import com.example.weatherapplication.ui.weather.detail_forecast.DetailsForecastViewModelFactory
import com.example.weatherapplication.ui.weather.report.ReportViewModelFactory
import com.example.weatherapplication.ui.weather.search_city.SearchCityViewModelFactory
import com.example.weatherapplication.ui.weather.short_forecast.ShortForecastViewModelFactory
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class ViewModelFactoryModule {

    @Provides
    fun provideShortForecastViewModelFactory(
        remoteRepo: RemoteRepository,
        appDisposable: AppDisposable,
        forecastDbRepo: ForecastDbRepository
    ): ShortForecastViewModelFactory {
        return ShortForecastViewModelFactory(
            remoteRepo = remoteRepo,
            appDisposable = appDisposable,
            forecastDbRepo = forecastDbRepo
        )
    }

    @Provides
    fun provideReportViewModelFactory(
        compositeDisposable: CompositeDisposable,
        remoteRepo: RemoteRepository,
        memoryRepository: MemoryRepository
    ): ReportViewModelFactory {
        return ReportViewModelFactory(
            compositeDisposable = compositeDisposable,
            remoteRepo = remoteRepo,
            memoryRepository = memoryRepository
        )
    }

    @Provides
    fun providerSearchCityViewModelFactory(
        remoteRepository: RemoteRepository,
        compositeDisposable: CompositeDisposable
    ): SearchCityViewModelFactory {
        return SearchCityViewModelFactory(
            remoteRepository = remoteRepository,
            compositeDisposable = compositeDisposable
        )
    }

    @Provides
    fun provideDetailsForecastViewModelFactory(): DetailsForecastViewModelFactory {
        return DetailsForecastViewModelFactory()
    }
}