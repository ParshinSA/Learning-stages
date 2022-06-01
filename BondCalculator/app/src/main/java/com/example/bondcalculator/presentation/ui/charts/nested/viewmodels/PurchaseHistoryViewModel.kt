package com.example.bondcalculator.presentation.ui.charts.nested.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bondcalculator.common.roundDouble
import com.example.bondcalculator.common.toDateString
import com.example.bondcalculator.domain.interactors.PurchaseHistoryFrgInteractor
import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForPurchaseHistoryFrg
import com.example.bondcalculator.presentation.models.PurchaseHistoryItem
import com.example.bondcalculator.presentation.viewmodels.BaseViewModel
import javax.inject.Inject

class PurchaseHistoryViewModel @Inject constructor(
    private val interactor: PurchaseHistoryFrgInteractor
) : BaseViewModel() {

    private val dataPurchaseHistoryMutLiveData = MutableLiveData<List<PurchaseHistoryItem>>()
    val dataPurchaseHistoryLiveData: LiveData<List<PurchaseHistoryItem>>
        get() = dataPurchaseHistoryMutLiveData

    fun getData() {
        compositeDisposable.add(
            interactor.getDataForPurchaseHistoryFrg()
                .subscribe({ data ->
                    val convertData = covertToPurchaseHistoryItem(data)
                    dataPurchaseHistoryMutLiveData.postValue(convertData)
                    Log.d(TAG, "start: result $data")
                }, {
                    Log.d(TAG, "start: ERROR $it")
                })
        )
    }

    private fun covertToPurchaseHistoryItem(data: DomainDataForPurchaseHistoryFrg): List<PurchaseHistoryItem> {
        val listItemsPurchaseHistory = mutableListOf<PurchaseHistoryItem>()
        var total = data.startBalance
        var sumCouponPayment = 0.0
        var sumAmortisationPayment = 0.0
        var previousYearPayment = 0.0

        for (year in data.allYearsInCalculatePeriod) {
            total -= sumAmortisationPayment

            val allSum = total + sumAmortisationPayment + sumCouponPayment + previousYearPayment
            var percentTotal = ((100 / allSum * total) / 100).roundDouble().toFloat()
            if (percentTotal == 1f) percentTotal = 0.99f

            var percentPreviousYearPayment =
                ((100 / allSum * previousYearPayment) / 100).roundDouble().toFloat()
            if (percentPreviousYearPayment == 1f) percentPreviousYearPayment = 0.99f

            var percentSumPayments =
                ((100 / allSum * (sumAmortisationPayment + sumCouponPayment)) / 100).roundDouble()
                    .toFloat()
            if (percentSumPayments == 1f) percentSumPayments = 0.99f


            listItemsPurchaseHistory.add(
                PurchaseHistoryItem(
                    year = year,
                    percentTotal = percentTotal,
                    percentPreviousYearPayment = percentPreviousYearPayment,
                    percentSumPayments = percentSumPayments
                )
            )

            previousYearPayment = sumAmortisationPayment + sumCouponPayment
            sumAmortisationPayment = data.amortizationPaymentsStepYear[year] ?: 0.0
            sumCouponPayment = data.couponPaymentsStepYear[year] ?: 0.0
        }
        return listItemsPurchaseHistory
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared: ")
        super.onCleared()
    }

    companion object {
        private const val TAG = "PurchaseHistoryViewModel"
    }
}