package com.example.bondcalculator.presentation.ui.charts.nested.viewmodels

import android.util.Log
import com.example.bondcalculator.domain.interactors.PayoutsFrgInteractor
import com.example.bondcalculator.presentation.viewmodels.BaseViewModel
import javax.inject.Inject

class PayoutsViewModel @Inject constructor(
    private val interactor: PayoutsFrgInteractor
) : BaseViewModel() {

    fun start() {
        compositeDisposable.add(
            interactor.getDataForPayoutsFrg()
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
        private const val TAG = "PayoutsViewModel"
    }
}