package com.example.weatherapplication.app.di.modules

import android.content.Context
import android.content.SharedPreferences
import androidx.work.WorkManager
import com.example.weatherapplication.app.common.contracts.SharedPrefsContract
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    @Singleton
    fun provideSharedPreferenceSingleObserver(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            SharedPrefsContract.SHARED_PREFS_NAME,
            Context.MODE_PRIVATE
        )
    }

    @Provides
    fun provideWorkManager(context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }
}