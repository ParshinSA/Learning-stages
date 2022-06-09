package com.example.bondcalculator.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bondcalculator.R
import com.example.bondcalculator.common.roundDouble
import com.example.bondcalculator.domain.interactors.PurchaseHistoryFrgInteractor
import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForPurchaseHistoryFrg
import com.example.bondcalculator.presentation.common.ResourcesProvider
import com.example.bondcalculator.presentation.models.PurchaseHistoryItemRv
import javax.inject.Inject

class PurchaseHistoryViewModel @Inject constructor(
    private val interactor: PurchaseHistoryFrgInteractor,
    private val resourcesProvider: ResourcesProvider,
) : BaseViewModel() {

    private val dataPurchaseHistoryMutLiveData = MutableLiveData<List<PurchaseHistoryItemRv>>()
    val dataPurchaseHistoryLiveData: LiveData<List<PurchaseHistoryItemRv>>
        get() = dataPurchaseHistoryMutLiveData

    fun getData() {
        compositeDisposable.add(
            interactor.getDataForPurchaseHistoryFrg()
                .subscribe({ data ->
                    val convertData = covertToPurchaseHistoryItem(data)
                    dataPurchaseHistoryMutLiveData.postValue(convertData)
                }, {
                    Log.d(TAG, "getData: $it")
                    errorMessage(resourcesProvider.getString(R.string.dialog_error_get_data_from_shared_prefs))
                })
        )
    }

    private fun covertToPurchaseHistoryItem(data: DomainDataForPurchaseHistoryFrg): List<PurchaseHistoryItemRv> {
        val listItemsPurchaseHistory = mutableListOf<PurchaseHistoryItemRv>()
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
                PurchaseHistoryItemRv(
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
        compositeDisposable.clear()
        super.onCleared()
    }

    companion object {
        private const val TAG = "PurchaseHistoryViewModel"
    }
}