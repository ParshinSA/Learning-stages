package com.example.weatherapplication.ui.weather.detail_forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DetailsForecastViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailsForecastViewModel() as T
    }
}