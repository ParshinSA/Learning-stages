package com.example.weatherapplication.data.models.report

import com.google.gson.annotations.SerializedName
import com.example.weatherapplication.data.models.report.HistoryWeatherContract.GsonName


data class Field(
    @SerializedName(GsonName.MEDIAN)
    val median: Double
)
