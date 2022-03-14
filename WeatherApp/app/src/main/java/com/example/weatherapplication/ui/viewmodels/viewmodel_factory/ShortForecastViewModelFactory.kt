package com.example.weatherapplication.ui.viewmodels.viewmodel_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapplication.data.repositories.repo_interface.CustomCitiesDbRepository
import com.example.weatherapplication.data.repositories.repo_interface.ForecastDbRepository
import com.example.weatherapplication.data.repositories.repo_interface.RemoteRepository
import com.example.weatherapplication.ui.viewmodels.viewmodel_classes.ShortForecastViewModel
import javax.inject.Inject

class ShortForecastViewModelFactory @Inject constructor(
    private val remoteRepo: RemoteRepository,
    private val forecastDbRepo: ForecastDbRepository,
    private val customCitiesDbRepo: CustomCitiesDbRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ShortForecastViewModel(
            remoteRepo = remoteRepo,
            forecastDbRepo = forecastDbRepo,
            customCitiesDbRepo = customCitiesDbRepo
        ) as T
    }
}
