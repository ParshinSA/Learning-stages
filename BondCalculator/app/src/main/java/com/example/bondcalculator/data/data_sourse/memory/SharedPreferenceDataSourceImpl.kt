package com.example.bondcalculator.data.data_sourse.memory

import android.content.SharedPreferences
import com.example.bondcalculator.common.*
import com.example.bondcalculator.domain.models.analysis_portfolio_yield.*
import com.google.gson.Gson
import io.reactivex.Single
import java.io.IOException
import javax.inject.Inject

class SharedPreferenceDataSourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SharedPreferenceDataSource {

    override fun saveDataForPortfolioFrg(data: DomainDataForPortfolioFrg) {
        saveDataByKey(data, KEY_DATA_FROM_PORTFOLIO_FRG)
    }

    override fun getDataForPortfolioFrg(): Single<DomainDataForPortfolioFrg> {
        return getDataByKey(KEY_DATA_FROM_PORTFOLIO_FRG, DomainDataForPortfolioFrg::class.java)
    }

    override fun saveDataForPayoutsFrg(data: DomainDataForPayoutsFrg) {
        saveDataByKey(data, KEY_DATA_FROM_PAYOUTS_FRG)
    }

    override fun getDataForPayoutsFrg(): Single<DomainDataForPayoutsFrg> {
        return getDataByKey(KEY_DATA_FROM_PAYOUTS_FRG, DomainDataForPayoutsFrg::class.java)
    }

    override fun saveDataForPurchaseHistoryFrg(data: DomainDataForPurchaseHistoryFrg) {
        saveDataByKey(data, KEY_DATA_FROM_PURCHASE_HISTORY_FRG)
    }

    override fun getDataForPurchaseHistoryFrg(): Single<DomainDataForPurchaseHistoryFrg> {
        return getDataByKey(
            KEY_DATA_FROM_PURCHASE_HISTORY_FRG,
            DomainDataForPurchaseHistoryFrg::class.java
        )
    }

    override fun saveDataForTextInfoDepositFrg(data: DomainDataForTextInfoDepositFrg) {
        saveDataByKey(data, KEY_DATA_FROM_TEXT_INFO_DEPOSIT_FRG)
    }

    override fun getDataForTextInfoDepositFrg(): Single<DomainDataForTextInfoDepositFrg> {
        return getDataByKey(
            KEY_DATA_FROM_TEXT_INFO_DEPOSIT_FRG,
            DomainDataForTextInfoDepositFrg::class.java
        )
    }

    override fun saveDataForCompositionFrg(data: DomainDataForCompositionFrg) {
        saveDataByKey(data, KEY_DATA_FROM_COMPOSITION_FRG)
    }

    override fun getDataForCompositionFrg(): Single<DomainDataForCompositionFrg> {
        return getDataByKey(KEY_DATA_FROM_COMPOSITION_FRG, DomainDataForCompositionFrg::class.java)
    }

    private fun <T> getDataByKey(key: String, type: Class<T>): Single<T> {
        return Single.create { subscription ->
            val dataString = sharedPreferences.getString(key, "")
            if (dataString == "") subscription.onError(IOException("empty SharedPrefs $type"))

            val data = Gson().fromJson(dataString, type)
            (data as T)?.let {
                subscription.onSuccess(it)
            }
        }
    }

    private fun saveDataByKey(data: Any, key: String) {
        sharedPreferences.edit()
            .putString(key, Gson().toJson(data))
            .apply()
    }
}