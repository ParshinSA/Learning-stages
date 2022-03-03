package com.example.weatherapplication.di

import com.example.weatherapplication.di.modules.*
import com.example.weatherapplication.services.UpdateForecastService
import com.example.weatherapplication.ui.AppActivity
import com.example.weatherapplication.ui.weather.detail_forecast.DetailsForecastFragment
import com.example.weatherapplication.ui.weather.report.ReportFragment
import com.example.weatherapplication.ui.weather.search_city.SearchCityFragment
import com.example.weatherapplication.ui.weather.short_forecast.ShortForecastListFragment
import com.example.weatherapplication.workers.UpdateForecastWorker
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DatabaseModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
        ObjectModule::class
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
}