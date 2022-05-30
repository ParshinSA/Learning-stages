package com.example.bondcalculator.presentation.ui.charts.nested.viewmodels

import android.util.Log
import com.example.bondcalculator.domain.interactors.PurchaseHistoryFrgInteractor
import com.example.bondcalculator.presentation.viewmodels.BaseViewModel
import javax.inject.Inject

class PurchaseHistoryViewModel @Inject constructor(
    private val interactor: PurchaseHistoryFrgInteractor
) : BaseViewModel() {

    fun start() {
        compositeDisposable.add(
            interactor.getDataForPurchaseHistoryFrg()
                .subscribe({
                    Log.d(TAG, "start: result $it")
                }, {
                    Log.d(TAG, "start: ERROR $it")
                })
        )
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared: ")
        super.onCleared()
    }

    companion object {
        private const val TAG = "PurchaseHistoryViewModel"
    }
}