package com.example.bondcalculator.presentation.ui.charts.nested.viewmodels.viewmodels_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bondcalculator.domain.interactors.PurchaseHistoryFrgInteractor
import com.example.bondcalculator.presentation.ui.charts.nested.viewmodels.PurchaseHistoryViewModel
import javax.inject.Inject

class PurchaseHistoryViewModelFactory @Inject constructor(
    private val interactor: PurchaseHistoryFrgInteractor
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PurchaseHistoryViewModel(
            interactor = interactor
        ) as T
    }
}