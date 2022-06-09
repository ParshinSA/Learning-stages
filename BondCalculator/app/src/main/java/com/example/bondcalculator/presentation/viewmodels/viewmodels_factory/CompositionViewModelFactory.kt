package com.example.bondcalculator.presentation.viewmodels.viewmodels_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bondcalculator.domain.interactors.CompositionFrgInteractor
import com.example.bondcalculator.presentation.common.ResourcesProvider
import com.example.bondcalculator.presentation.viewmodels.CompositionViewModel
import javax.inject.Inject

class CompositionViewModelFactory @Inject constructor(
    private val interactor: CompositionFrgInteractor,
    private val resourcesProvider: ResourcesProvider,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CompositionViewModel(
            interactor = interactor,
            resourcesProvider = resourcesProvider
        ) as T
    }
}