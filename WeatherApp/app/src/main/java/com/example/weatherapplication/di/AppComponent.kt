package com.example.weatherapplication.di

import android.content.Context
import com.example.weatherapplication.di.modules.AppModule
import com.example.weatherapplication.di.modules.DatabaseModule
import com.example.weatherapplication.di.modules.NetworkModule
import com.example.weatherapplication.di.modules.RepositoryModule
import com.example.weatherapplication.services.UpdateForecastService
import com.example.weatherapplication.ui.activities.AppActivity
import com.example.weatherapplication.ui.fragments.DetailsForecastFragment
import com.example.weatherapplication.ui.fragments.ReportFragment
import com.example.weatherapplication.ui.fragments.SearchCityFragment
import com.example.weatherapplication.ui.fragments.ShortForecastListFragment
import com.example.weatherapplication.workers.UpdateForecastWorker
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DatabaseModule::class,
        RepositoryModule::class,
        NetworkModule::class
    ]
)
interface AppComponent {

    fun inject(activity: AppActivity)

    fun inject(fragment: ShortForecastListFragment)
    fun inject(fragment: SearchCityFragment)
    fun inject(fragment: DetailsForecastFragment)
    fun inject(fragment: ReportFragment)

    fun inject(service: UpdateForecastService)

    fun inject(worker: UpdateForecastWorker)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}