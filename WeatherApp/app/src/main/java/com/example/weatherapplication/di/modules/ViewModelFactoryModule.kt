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
        forecastDbRepo: ForecastDbRepository
    ): ShortForecastViewModelFactory {
        return ShortForecastViewModelFactory(
            remoteRepo = remoteRepo,
            forecastDbRepo = forecastDbRepo
        )
    }

    @Provides
    fun provideReportViewModelFactory(
        remoteRepo: RemoteRepository,
        memoryRepo: MemoryRepository
    ): ReportViewModelFactory {
        return ReportViewModelFactory(
            remoteRepo = remoteRepo,
            memoryRepository = memoryRepo
        )
    }

    @Provides
    fun providerSearchCityViewModelFactory(
        remoteRepo: RemoteRepository,
        customCitiesDbRepo: CustomCitiesDbRepository
    ): SearchCityViewModelFactory {
        return SearchCityViewModelFactory(
            remoteRepo = remoteRepo,
            customCitiesDbRepo = customCitiesDbRepo
        )
    }

    @Provides
    fun provideDetailsForecastViewModelFactory(): DetailsForecastViewModelFactory {
        return DetailsForecastViewModelFactory()
    }
}