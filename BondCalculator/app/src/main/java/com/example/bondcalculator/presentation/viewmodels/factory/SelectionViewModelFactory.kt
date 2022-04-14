package com.example.bondcalculator.presentation.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bondcalculator.data.data_source.intf.RequestSecuritiesDataDataSource
import com.example.bondcalculator.domain.interactors.intf.SecuritiesDataInteractor
import com.example.bondcalculator.presentation.viewmodels.SelectionViewModel
import javax.inject.Inject

class SelectionViewModelFactory @Inject constructor(
    private val interactor: SecuritiesDataInteractor
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SelectionViewModel(
            interactor = interactor
        ) as T
    }
}