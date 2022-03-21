package com.example.weatherapplication.presentation.viewmodels.viewmodel_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapplication.presentation.viewmodels.viewmodel_classes.DetailsForecastViewModel
import javax.inject.Inject

class DetailsForecastViewModelFactory @Inject constructor() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailsForecastViewModel() as T
    }
}