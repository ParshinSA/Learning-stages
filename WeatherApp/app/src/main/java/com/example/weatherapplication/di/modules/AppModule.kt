package com.example.weatherapplication.di.modules

import android.content.Context
import android.content.SharedPreferences
import androidx.work.WorkManager
import com.example.weatherapplication.data.common.SharedPrefsContract
import com.example.weatherapplication.common.AppState
import com.example.weatherapplication.data.repositories.repo_interface.CustomCitiesDbRepository
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
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