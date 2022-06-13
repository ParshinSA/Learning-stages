package com.example.bondcalculator.presentation.viewmodels.viewmodels_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bondcalculator.domain.interactors.PurchaseHistoryFrgInteractor
import com.example.bondcalculator.presentation.common.ResourcesProvider
import com.example.bondcalculator.presentation.viewmodels.PurchaseHistoryViewModel
import javax.inject.Inject

class PurchaseHistoryViewModelFactory @Inject constructor(
    private val interactor: PurchaseHistoryFrgInteractor,
    private val resourcesProvider: ResourcesProvider,
    ) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PurchaseHistoryViewModel(
            interactor = interactor,
            resourcesProvider = resourcesProvider
        ) as T
    }
}