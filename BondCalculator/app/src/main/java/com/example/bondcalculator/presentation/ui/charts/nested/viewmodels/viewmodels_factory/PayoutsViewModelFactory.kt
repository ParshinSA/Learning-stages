package com.example.bondcalculator.presentation.ui.charts.nested.viewmodels.viewmodels_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bondcalculator.domain.interactors.PayoutsFrgInteractor
import com.example.bondcalculator.presentation.ui.charts.nested.viewmodels.PayoutsViewModel
import javax.inject.Inject

class PayoutsViewModelFactory @Inject constructor(
    private val interactor: PayoutsFrgInteractor
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PayoutsViewModel(
            interactor = interactor
        ) as T
    }
}