package com.example.weatherapplication.presentation.viewmodels.viewmodel_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapplication.domain.interactors.interactors_interface.ForecastInteractor
import com.example.weatherapplication.presentation.ui.common.ResourcesProvider
import com.example.weatherapplication.presentation.viewmodels.viewmodel_classes.ShortForecastViewModel
import javax.inject.Inject

class ShortForecastViewModelFactory @Inject constructor(
    private val interactor: ForecastInteractor,
    private val resourcesProvider: ResourcesProvider
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ShortForecastViewModel(
            interactor = interactor,
            resourcesProvider = resourcesProvider
        ) as T
    }
}
