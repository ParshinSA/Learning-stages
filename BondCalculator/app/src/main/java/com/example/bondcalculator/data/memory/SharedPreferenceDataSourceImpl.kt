package com.example.bondcalculator.data.memory

import android.content.SharedPreferences
import com.example.bondcalculator.common.KEY_SAVE_CALCULATE_IN_SP
import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioYield
import com.google.gson.Gson
import javax.inject.Inject

class SharedPreferenceDataSourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SharedPreferenceDataSource {

    override fun saveCalculate(calculate: DomainPortfolioYield) {
        sharedPreferences.edit()
            .putString(KEY_SAVE_CALCULATE_IN_SP, Gson().toJson(calculate))
            .apply()
    }

    override fun getCalculate(): DomainPortfolioYield {
        val result = sharedPreferences.getString(KEY_SAVE_CALCULATE_IN_SP, "error")
        if (result == "error") error("ERROR SharedPrefs empty")
        return Gson().fromJson(result, DomainPortfolioYield::class.java)
    }
}