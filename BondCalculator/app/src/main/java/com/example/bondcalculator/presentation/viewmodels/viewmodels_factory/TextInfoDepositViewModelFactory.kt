package com.example.bondcalculator.presentation.viewmodels.viewmodels_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bondcalculator.domain.interactors.TextInfoDepositFrgInteractor
import com.example.bondcalculator.presentation.common.ResourcesProvider
import com.example.bondcalculator.presentation.viewmodels.TextInfoDepositViewModel
import javax.inject.Inject

class TextInfoDepositViewModelFactory @Inject constructor(
    private val interactor: TextInfoDepositFrgInteractor,
    private val resourcesProvider: ResourcesProvider
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TextInfoDepositViewModel(
            interactor = interactor,
            resourcesProvider = resourcesProvider
        ) as T
    }
}