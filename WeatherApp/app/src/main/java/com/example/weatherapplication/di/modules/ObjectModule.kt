package com.example.weatherapplication.di.modules

import android.content.Context
import android.content.SharedPreferences
import androidx.work.WorkManager
import com.example.weatherapplication.data.db.app_sp.SharedPrefsContract
import com.example.weatherapplication.data.objects.AppDisposable
import com.example.weatherapplication.data.objects.AppState
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
    @Singleton
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    @Singleton
    fun provideSharedPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            SharedPrefsContract.SHARED_PREFS_NAME,
            Context.MODE_PRIVATE
        )
    }

    @Provides
    @Singleton
    fun provideAppState(): AppState {
        return AppState()
    }

    @Provides
    @Singleton
    fun provideWorkManager(context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }
}