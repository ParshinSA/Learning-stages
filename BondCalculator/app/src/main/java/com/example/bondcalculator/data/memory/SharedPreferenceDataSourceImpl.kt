package com.example.bondcalculator.data.memory

import android.content.SharedPreferences
import com.example.bondcalculator.common.KEY_DATA_FROM_PAYOUTS_FRG
import com.example.bondcalculator.common.KEY_DATA_FROM_PORTFOLIO_FRG
import com.example.bondcalculator.common.KEY_DATA_FROM_PURCHASE_HISTORY_FRG
import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForPayoutsFrg
import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForPortfolioFrg
import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForPurchaseHistoryFrg
import com.google.gson.Gson
import io.reactivex.Single
import java.io.IOException
import javax.inject.Inject

class SharedPreferenceDataSourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SharedPreferenceDataSource {

    override fun saveDataForPortfolioFrg(data: DomainDataForPortfolioFrg) {
        sharedPreferences.edit()
            .putString(KEY_DATA_FROM_PORTFOLIO_FRG, Gson().toJson(data))
            .apply()
    }

    override fun getDataForPortfolioFrg(): Single<DomainDataForPortfolioFrg> {
        return Single.create { subscription ->
            val dataString = sharedPreferences.getString(KEY_DATA_FROM_PORTFOLIO_FRG, "")
            if (dataString == "") subscription.onError(IOException("empty SharedPrefs"))

            val data = Gson().fromJson(dataString, DomainDataForPortfolioFrg::class.java)
            subscription.onSuccess(data)
        }
    }

    override fun saveDataForPayoutsFrg(data: DomainDataForPayoutsFrg) {
        sharedPreferences.edit()
            .putString(KEY_DATA_FROM_PAYOUTS_FRG, Gson().toJson(data))
            .apply()
    }

    override fun getDataForPayoutsFrg(): Single<DomainDataForPayoutsFrg> {
        return Single.create { subscription ->
            val dataString = sharedPreferences.getString(KEY_DATA_FROM_PAYOUTS_FRG, "")
            if (dataString == "") subscription.onError(IOException("empty SharedPrefs"))

            val data = Gson().fromJson(dataString, DomainDataForPayoutsFrg::class.java)
            subscription.onSuccess(data)
        }
    }

    override fun saveDataForPurchaseHistoryFrg(data: DomainDataForPurchaseHistoryFrg) {
        sharedPreferences.edit()
            .putString(KEY_DATA_FROM_PURCHASE_HISTORY_FRG, Gson().toJson(data))
            .apply()
    }

    override fun getDataForPurchaseHistoryFrg(): Single<DomainDataForPurchaseHistoryFrg> {
        return Single.create { subscription ->
            val dataString = sharedPreferences.getString(KEY_DATA_FROM_PURCHASE_HISTORY_FRG, "")
            if (dataString == "") subscription.onError(IOException("empty SharedPrefs"))

            val data = Gson().fromJson(dataString, DomainDataForPurchaseHistoryFrg::class.java)
            subscription.onSuccess(data)
        }
    }

}