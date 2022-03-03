package com.example.weatherapplication.di.modules

import com.example.weatherapplication.data.objects.AppDisposable
import com.example.weatherapplication.data.objects.CustomCities
import com.example.weatherapplication.data.repositories.repo_interface.CustomCitiesRepository
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class ObjectModule {

    @Provides
    @Singleton
    fun provideCustomCities(
        customCitiesRepository: CustomCitiesRepository,
        appDisposable: AppDisposable
    ): CustomCities {
        return CustomCities(
            customCitiesRepository = customCitiesRepository,
            appDisposable = appDisposable
        )
    }

    @Provides
    @Singleton
    fun provideDisposeBack(): AppDisposable {
        return AppDisposable()
    }

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }
}