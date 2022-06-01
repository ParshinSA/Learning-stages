package com.example.bondcalculator.presentation.ui.charts.nested.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bondcalculator.common.roundDouble
import com.example.bondcalculator.domain.interactors.PayoutsFrgInteractor
import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForPayoutsFrg
import com.example.bondcalculator.presentation.models.PayoutsItem
import com.example.bondcalculator.presentation.viewmodels.BaseViewModel
import javax.inject.Inject

class PayoutsViewModel @Inject constructor(
    private val interactor: PayoutsFrgInteractor
) : BaseViewModel() {

    private val dataPayoutsMutLiveData = MutableLiveData<List<PayoutsItem>>()
    val dataPayoutsLiveData: LiveData<List<PayoutsItem>> get() = dataPayoutsMutLiveData

    fun getData() {
        compositeDisposable.add(
            interactor.getDataForPayoutsFrg()
                .subscribe({ data ->
                    Log.d(TAG, "getData: $data")
                    val convertData = convertToPayoutsItem(data)
                    dataPayoutsMutLiveData.postValue(convertData)
                }, {
                    Log.d(TAG, "start: ERROR $it")
                })
        )
    }

    private fun convertToPayoutsItem(data: DomainDataForPayoutsFrg): List<PayoutsItem> {
        val listPayoutsItems = mutableListOf<PayoutsItem>()

        for (year in data.allYearsInCalculatePeriod) {

            val couponYield = data.couponPaymentsStepYear[year] ?: 0.0
            val redemptionYield = data.amortizationPaymentsStepYear[year] ?: 0.0
            val sumYield = couponYield + redemptionYield

            var couponPercentYield = ((100 / sumYield * couponYield) / 100).toFloat()
            if (couponPercentYield == 1.0f) couponPercentYield = 0.99f

            var redemptionPercentYield = ((100 / sumYield * redemptionYield) / 100).toFloat()
            if (redemptionPercentYield == 1.0f) redemptionPercentYield = 0.99f

            listPayoutsItems.add(
                PayoutsItem(
                    year = year,
                    sumYield = sumYield.roundDouble(),
                    percentCouponYield = couponPercentYield,
                    percentRedemptionYield = redemptionPercentYield
                )
            )
        }
        return listPayoutsItems
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared: ")
        super.onCleared()
    }

    companion object {
        private const val TAG = "PayoutsViewModel"
    }
}