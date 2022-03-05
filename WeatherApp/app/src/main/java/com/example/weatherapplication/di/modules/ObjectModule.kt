package com.example.weatherapplication.di.modules

import android.content.Context
import android.content.SharedPreferences
import androidx.work.WorkManager
import com.example.weatherapplication.data.db.app_sp.SharedPrefsContract
import com.example.weatherapplication.data.objects.AppDisposable
import com.example.weatherapplication.data.objects.AppState
import com.example.weatherapplication.data.objects.CustomCities
import com.example.weatherapplication.data.repositories.repo_interface.CustomCitiesDbRepository
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class ObjectModule {

    @Provides
    @Singleton
    fun provideCustomCities(
        customCitiesDbRepository: CustomCitiesDbRepository,
        appDisposable: AppDisposable
    ): CustomCities {
        return CustomCities(
            customCitiesDbRepository = customCitiesDbRepository,
            appDisposable = appDisposable
        )
    }

    @Provides
    @Singleton
    fun provideAppDisposable(compositeDisposable: CompositeDisposable): AppDisposable {
        return AppDisposable(compositeDisposable)
    }

    @Provides
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
    fun provideWorkManager(context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }
}