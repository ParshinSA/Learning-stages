package com.example.weatherapplication.ui.viewmodels.viewnodels_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapplication.ui.viewmodels.viewmodels.DetailsForecastViewModel

class DetailsForecastViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailsForecastViewModel() as T
    }
}