package com.example.weatherapplication.ui.viewmodels.viewmodel_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapplication.data.repositories.repo_interface.CustomCitiesDbRepository
import com.example.weatherapplication.data.repositories.repo_interface.RemoteRepository
import com.example.weatherapplication.ui.viewmodels.viewmodel_classes.SearchCityViewModel
import javax.inject.Inject

class SearchCityViewModelFactory @Inject constructor(
    private val remoteRepo: RemoteRepository,
    private val customCitiesDbRepo: CustomCitiesDbRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchCityViewModel(
            remoteRepo = remoteRepo,
            customCitiesDbRepo = customCitiesDbRepo
        ) as T
    }
}