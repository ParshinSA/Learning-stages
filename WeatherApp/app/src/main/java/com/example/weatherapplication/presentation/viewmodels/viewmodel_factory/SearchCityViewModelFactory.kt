package com.example.weatherapplication.presentation.viewmodels.viewmodel_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapplication.domain.interactors.interactors_interface.CustomCitiesInteractor
import com.example.weatherapplication.presentation.viewmodels.viewmodel_classes.CustomCityViewModel
import javax.inject.Inject

class SearchCityViewModelFactory @Inject constructor(
    private val interactor: CustomCitiesInteractor
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CustomCityViewModel(interactor = interactor) as T
    }
}