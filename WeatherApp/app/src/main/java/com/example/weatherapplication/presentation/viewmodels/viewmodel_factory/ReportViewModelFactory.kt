package com.example.weatherapplication.presentation.viewmodels.viewmodel_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapplication.domain.interactors.interactors_interface.ReportInteractor
import com.example.weatherapplication.presentation.viewmodels.viewmodel_classes.ReportViewModel
import javax.inject.Inject

class ReportViewModelFactory @Inject constructor(
    private val interactor: ReportInteractor
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ReportViewModel(interactor = interactor) as T
    }
}