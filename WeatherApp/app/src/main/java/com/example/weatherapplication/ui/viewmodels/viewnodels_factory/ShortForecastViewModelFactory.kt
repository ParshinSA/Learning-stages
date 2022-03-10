package com.example.weatherapplication.ui.viewmodels.viewnodels_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapplication.data.repositories.repo_interface.ForecastDbRepository
import com.example.weatherapplication.data.repositories.repo_interface.RemoteRepository
import com.example.weatherapplication.ui.viewmodels.viewmodels.ShortForecastViewModel
import io.reactivex.disposables.CompositeDisposable

class ShortForecastViewModelFactory(
    private val remoteRepo: RemoteRepository,
    private val forecastDbRepo: ForecastDbRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ShortForecastViewModel(
            remoteRepo = remoteRepo,
            forecastDbRepo = forecastDbRepo
        ) as T
    }
}
