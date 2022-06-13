package com.example.bondcalculator.presentation.viewmodels.viewmodels_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bondcalculator.domain.interactors.PortfolioFrgInteractor
import com.example.bondcalculator.presentation.common.ResourcesProvider
import com.example.bondcalculator.presentation.viewmodels.PortfolioViewModel
import javax.inject.Inject

class PortfolioViewModelFactory @Inject constructor(
    private val interactor: PortfolioFrgInteractor,
    private val resourcesProvider: ResourcesProvider
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PortfolioViewModel(
            interactor = interactor,
            resourcesProvider = resourcesProvider
        ) as T
    }
}