package com.example.bondcalculator.presentation.viewmodels.viewmodels_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bondcalculator.domain.interactors.PayoutsFrgInteractor
import com.example.bondcalculator.presentation.common.ResourcesProvider
import com.example.bondcalculator.presentation.viewmodels.PayoutsViewModel
import javax.inject.Inject

class PayoutsViewModelFactory @Inject constructor(
    private val interactor: PayoutsFrgInteractor,
    private val resourcesProvider: ResourcesProvider,
    ) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PayoutsViewModel(
            interactor = interactor,
            resourcesProvider = resourcesProvider
        ) as T
    }
}