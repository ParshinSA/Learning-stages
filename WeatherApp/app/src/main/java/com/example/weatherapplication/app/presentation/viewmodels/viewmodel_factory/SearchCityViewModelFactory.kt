package com.example.weatherapplication.app.presentation.viewmodels.viewmodel_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapplication.data.data_source.custom_cities.RoomCustomCitiesDataSource
import com.example.weatherapplication.data.data_source.forecast.RemoteForecastDataSource
import com.example.weatherapplication.app.presentation.viewmodels.viewmodel_classes.SearchCityViewModel
import javax.inject.Inject

class SearchCityViewModelFactory @Inject constructor(
    private val remoteRepo: RemoteForecastDataSource,
    private val roomCustomCitiesDbRepo: RoomCustomCitiesDataSource
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchCityViewModel(
            remoteRepo = remoteRepo,
            customCitiesDbRepo = roomCustomCitiesDbRepo
        ) as T
    }
}