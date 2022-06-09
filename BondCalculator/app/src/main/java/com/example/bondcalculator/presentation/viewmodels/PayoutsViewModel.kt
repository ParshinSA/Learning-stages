package com.example.bondcalculator.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bondcalculator.R
import com.example.bondcalculator.common.roundDouble
import com.example.bondcalculator.domain.interactors.PayoutsFrgInteractor
import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForPayoutsFrg
import com.example.bondcalculator.presentation.common.ResourcesProvider
import com.example.bondcalculator.presentation.models.PayoutsItemRv
import javax.inject.Inject

class PayoutsViewModel @Inject constructor(
    private val interactor: PayoutsFrgInteractor,
    private val resourcesProvider: ResourcesProvider
) : BaseViewModel() {

    private val dataPayoutsMutLiveData = MutableLiveData<List<PayoutsItemRv>>()
    val dataPayoutsLiveData: LiveData<List<PayoutsItemRv>> get() = dataPayoutsMutLiveData

    fun getData() {
        compositeDisposable.add(
            interactor.getDataForPayoutsFrg()
                .subscribe({ data ->
                    val convertData = convertToPayoutsItem(data)
                    dataPayoutsMutLiveData.postValue(convertData)
                }, {
                    Log.d(TAG, "getData: $it")
                    errorMessage(resourcesProvider.getString(R.string.dialog_error_get_data_from_shared_prefs))
                })
        )
    }

    private fun convertToPayoutsItem(data: DomainDataForPayoutsFrg): List<PayoutsItemRv> {
        val listPayoutsItems = mutableListOf<PayoutsItemRv>()

        for (year in data.allYearsInCalculatePeriod) {

            val couponYield = data.couponPaymentsStepYear[year] ?: 0.0
            val redemptionYield = data.amortizationPaymentsStepYear[year] ?: 0.0
            val sumYield = couponYield + redemptionYield

            var couponPercentYield = ((100 / sumYield * couponYield) / 100).toFloat()
            if (couponPercentYield == 1.0f) couponPercentYield = 0.99f

            var redemptionPercentYield = ((100 / sumYield * redemptionYield) / 100).toFloat()
            if (redemptionPercentYield == 1.0f) redemptionPercentYield = 0.99f

            listPayoutsItems.add(
                PayoutsItemRv(
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
        compositeDisposable.clear()
        super.onCleared()
    }

    companion object {
        private const val TAG = "PayoutsViewModel"
    }
}