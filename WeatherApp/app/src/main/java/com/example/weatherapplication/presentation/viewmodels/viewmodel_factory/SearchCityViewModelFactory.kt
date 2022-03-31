package com.example.weatherapplication.presentation.viewmodels.viewmodel_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapplication.domain.interactors.interactors_interface.CityInteractor
import com.example.weatherapplication.presentation.viewmodels.viewmodel_classes.CityViewModel
import javax.inject.Inject

class SearchCityViewModelFactory @Inject constructor(
    private val interactor: CityInteractor
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CityViewModel(
            interactor = interactor,
        ) as T
    }
}