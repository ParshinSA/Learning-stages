package com.example.weatherapplication.app.presentation.viewmodels.viewmodel_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapplication.data.data_source.forecast.RemoteForecastDataSource
import com.example.weatherapplication.app.presentation.viewmodels.viewmodel_classes.ReportViewModel
import javax.inject.Inject

class ReportViewModelFactory @Inject constructor(
    private val remoteRepo: RemoteForecastDataSource,
    private val memoryRepo: ReportDataSource
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ReportViewModel(
            remoteRepo = remoteRepo,
            memoryRepo = memoryRepo
        ) as T
    }
}