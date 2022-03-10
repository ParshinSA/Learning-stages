package com.example.weatherapplication.di.modules

import com.example.weatherapplication.data.repositories.repo_interface.CustomCitiesDbRepository
import com.example.weatherapplication.data.repositories.repo_interface.ForecastDbRepository
import com.example.weatherapplication.data.repositories.repo_interface.MemoryRepository
import com.example.weatherapplication.data.repositories.repo_interface.RemoteRepository
import com.example.weatherapplication.ui.viewmodels.viewnodels_factory.DetailsForecastViewModelFactory
import com.example.weatherapplication.ui.viewmodels.viewnodels_factory.ReportViewModelFactory
import com.example.weatherapplication.ui.viewmodels.viewnodels_factory.SearchCityViewModelFactory
import com.example.weatherapplication.ui.viewmodels.viewnodels_factory.ShortForecastViewModelFactory
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class ViewModelFactoryModule {

    @Provides
    fun provideShortForecastViewModelFactory(
        remoteRepo: RemoteRepository,
        compositeDisposable: CompositeDisposable,
        forecastDbRepo: ForecastDbRepository
    ): ShortForecastViewModelFactory {
        return ShortForecastViewModelFactory(
            remoteRepo = remoteRepo,
            compositeDisposable = compositeDisposable,
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
        compositeDisposable: CompositeDisposable,
        customCitiesDbRepo: CustomCitiesDbRepository
    ): SearchCityViewModelFactory {
        return SearchCityViewModelFactory(
            remoteRepository = remoteRepository,
            compositeDisposable = compositeDisposable,
            customCitiesDbRepo = customCitiesDbRepo
        )
    }

    @Provides
    fun provideDetailsForecastViewModelFactory(): DetailsForecastViewModelFactory {
        return DetailsForecastViewModelFactory()
    }
}