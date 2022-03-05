package com.example.weatherapplication.ui.weather.search_city

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapplication.data.repositories.repo_interface.RemoteRepository
import io.reactivex.disposables.CompositeDisposable

class SearchCityViewModelFactory(
    private val remoteRepository: RemoteRepository,
    private val compositeDisposable: CompositeDisposable
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchCityViewModel(
            remoteRepository = remoteRepository,
            disposeBack = compositeDisposable
        ) as T
    }
}