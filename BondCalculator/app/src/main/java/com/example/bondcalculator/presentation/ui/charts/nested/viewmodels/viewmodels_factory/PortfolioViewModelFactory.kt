package com.example.bondcalculator.presentation.ui.charts.nested.viewmodels.viewmodels_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bondcalculator.domain.interactors.PortfolioFrgInteractor
import com.example.bondcalculator.presentation.common.ResourcesProvider
import com.example.bondcalculator.presentation.ui.charts.nested.viewmodels.PortfolioViewModel
import javax.inject.Inject

class PortfolioViewModelFactory @Inject constructor(
    private val interactor: PortfolioFrgInteractor,
    private val provider: ResourcesProvider
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PortfolioViewModel(
            interactor = interactor,
            provider = provider
        ) as T
    }
}