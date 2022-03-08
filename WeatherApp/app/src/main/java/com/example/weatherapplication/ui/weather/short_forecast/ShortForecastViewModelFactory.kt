package com.example.weatherapplication.ui.weather.short_forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapplication.data.repositories.repo_interface.CustomCitiesDbRepository
import com.example.weatherapplication.data.repositories.repo_interface.ForecastDbRepository
import com.example.weatherapplication.data.repositories.repo_interface.RemoteRepository
import io.reactivex.disposables.CompositeDisposable

class ShortForecastViewModelFactory(
    private val remoteRepo: RemoteRepository,
    private val compositeDisposable: CompositeDisposable,
    private val forecastDbRepo: ForecastDbRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ShortForecastViewModel(
            remoteRepo = remoteRepo,
            compositeDisposable = compositeDisposable,
            forecastDbRepo = forecastDbRepo,
        ) as T
    }
}
