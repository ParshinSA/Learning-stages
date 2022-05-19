package com.example.bondcalculator.presentation.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bondcalculator.domain.interactors.SelectedFrgInteractor
import com.example.bondcalculator.presentation.common.ResourcesProvider
import com.example.bondcalculator.presentation.viewmodels.SelectionViewModel
import javax.inject.Inject

class SelectionViewModelFactory @Inject constructor(
    private val interactor: SelectedFrgInteractor,
    private val resourcesProvider: ResourcesProvider
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SelectionViewModel(
            interactor = interactor,
            resourcesProvider = resourcesProvider
        ) as T
    }
}