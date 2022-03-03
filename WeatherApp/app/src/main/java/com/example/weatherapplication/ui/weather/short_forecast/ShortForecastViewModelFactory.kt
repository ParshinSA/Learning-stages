package com.example.weatherapplication.ui.weather.short_forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapplication.data.objects.AppDisposable
import com.example.weatherapplication.data.repositories.repo_interface.DatabaseRepository
import com.example.weatherapplication.data.repositories.repo_interface.RemoteRepository
import io.reactivex.disposables.CompositeDisposable

class ShortForecastViewModelFactory(
    private val remoteRepo: RemoteRepository,
    private val appDisposable: AppDisposable,
    private val databaseRepo: DatabaseRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ShortForecastViewModel(
            remoteRepo = remoteRepo,
            appDisposable = appDisposable,
            databaseRepo = databaseRepo
        ) as T
    }
}
