package com.example.bondcalculator.presentation.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bondcalculator.domain.interactors.ChartsFrgInteractor
import com.example.bondcalculator.presentation.viewmodels.ChartsViewModel
import javax.inject.Inject

class ChartsViewModelFactory @Inject constructor(
    private val interactor: ChartsFrgInteractor
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChartsViewModel(
            interactor = interactor
        ) as T
    }
}