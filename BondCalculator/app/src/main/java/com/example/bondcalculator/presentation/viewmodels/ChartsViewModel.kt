package com.example.bondcalculator.presentation.viewmodels

import android.util.Log
import com.example.bondcalculator.domain.interactors.ChartsFrgInteractor
import javax.inject.Inject

class ChartsViewModel @Inject constructor(
    private val interactor: ChartsFrgInteractor
) : BaseViewModel() {

    fun log() {
    }

    init {
        log()
        compositeDisposable.add(
            interactor.getCalculatePortfolioYield().subscribe({ calculate ->
                Log.d(TAG, "test: $calculate")
            }, { error ->
                Log.d(TAG, "test: error $error")
            })
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    companion object {
        private val TAG = this::class.qualifiedName
    }
}