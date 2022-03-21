package com.example.weatherapplication.di.modules

import android.content.Context
import android.content.SharedPreferences
import androidx.work.WorkManager
import com.example.weatherapplication.data.data_source.shared_preference.SharedPreferenceDataSource
import com.example.weatherapplication.domain.datasource_impl.shared_preference.SharedPreferenceDataSourceImpl
import com.example.weatherapplication.presentation.common.contracts.SharedPrefsContract
import dagger.Binds
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
    fun provideSharedPreference(context: Context): SharedPreferences {
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