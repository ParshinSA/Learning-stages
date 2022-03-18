package com.example.weatherapplication.app.di

import android.content.Context
import com.example.weatherapplication.app.di.modules.AppModule
import com.example.weatherapplication.app.di.modules.DatabaseModule
import com.example.weatherapplication.app.di.modules.NetworkModule
import com.example.weatherapplication.app.di.modules.RepositoryModule
import com.example.weatherapplication.app.services.UpdateForecastService
import com.example.weatherapplication.app.ui.activities.AppActivity
import com.example.weatherapplication.app.ui.fragments.DetailsForecastFragment
import com.example.weatherapplication.app.ui.fragments.ReportFragment
import com.example.weatherapplication.app.ui.fragments.SearchCityFragment
import com.example.weatherapplication.app.ui.fragments.ShortForecastFragment
import com.example.weatherapplication.app.workers.UpdateForecastWorker
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