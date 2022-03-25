package com.example.weatherapplication.di

import android.content.Context
import com.example.weatherapplication.di.modules.*
import com.example.weatherapplication.presentation.ui.activities.AppActivity
import com.example.weatherapplication.presentation.ui.fragments.SearchCityFragment
import com.example.weatherapplication.presentation.ui.fragments.DetailsForecastFragment
import com.example.weatherapplication.presentation.ui.fragments.ReportFragment
import com.example.weatherapplication.presentation.ui.fragments.ShortForecastFragment
import com.example.weatherapplication.services.UpdateForecastService
import com.example.weatherapplication.workers.UpdateForecastWorker
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DatabaseModule::class,
        DataSourceModule::class,
        NetworkModule::class,
        InteractorModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent {

    fun inject(activity: AppActivity)

    fun inject(fragment: ShortForecastFragment)
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