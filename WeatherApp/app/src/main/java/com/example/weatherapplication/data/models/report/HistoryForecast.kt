package com.example.weatherapplication.data.models.report

import com.google.gson.annotations.SerializedName
import com.example.weatherapplication.data.models.report.HistoryWeatherContract.GsonName

data class HistoryForecast(
    @SerializedName(GsonName.RESULT)
    val historyData: HistoryData
)
