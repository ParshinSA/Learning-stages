package com.example.weatherapplication.di.modules

import com.example.weatherapplication.data.repositories.repo_interface.DatabaseRepository
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
class ViewModelModule {

    @Provides
    fun provideShortForecastViewModelFactory(
        remoteRepo: RemoteRepository,
        disposable: CompositeDisposable,
        databaseRepo: DatabaseRepository
    ): ShortForecastViewModelFactory {
        return ShortForecastViewModelFactory(
            remoteRepo = remoteRepo,
            disposable = disposable,
            databaseRepo = databaseRepo
        )
    }

    @Provides
    fun provideReportViewModelFactory(
        disposeBack: CompositeDisposable,
        remoteRepo: RemoteRepository,
        memoryRepository: MemoryRepository
    ): ReportViewModelFactory {
        return ReportViewModelFactory(
            disposeBack = disposeBack,
            remoteRepo = remoteRepo,
            memoryRepository = memoryRepository
        )
    }

    @Provides
    fun providerSearchCityViewModelFactory(
        remoteRepository: RemoteRepository,
        disposeBack: CompositeDisposable
    ): SearchCityViewModelFactory {
        return SearchCityViewModelFactory(
            remoteRepository = remoteRepository,
            disposeBack = disposeBack
        )
    }

    @Provides
    fun provideDetailsForecastViewModelFactory(): DetailsForecastViewModelFactory {
        return DetailsForecastViewModelFactory()
    }
}