package com.example.bondcalculator.data.memory

import android.content.SharedPreferences
import android.util.Log
import com.example.bondcalculator.common.KEY_SAVE_CALCULATE_IN_SP
import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioYield
import com.google.gson.Gson
import io.reactivex.Observable
import javax.inject.Inject

class SharedPreferenceDataSourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SharedPreferenceDataSource {

    override fun saveCalculatePortfolioYield(calculate: DomainPortfolioYield) {

        sharedPreferences.edit()
            .putString(KEY_SAVE_CALCULATE_IN_SP, Gson().toJson(calculate))
            .apply()
    }

    override fun getCalculatePortfolioYield(): Observable<DomainPortfolioYield> {
        return Observable.create { subscription ->
            val result = sharedPreferences.getString(KEY_SAVE_CALCULATE_IN_SP, "error")
            Log.d("SharedPreferenceDataSourceImpl", "getCalculatePortfolioYield: $result")
            if (result == "error") error("ERROR SharedPrefs empty")
            val test = Gson().fromJson(result, DomainPortfolioYield::class.java)
            Log.d("SharedPreferenceDataSourceImpl", "getCalculatePortfolioYield: $test")

            subscription.onNext(test)
            subscription.onComplete()
        }
    }
}