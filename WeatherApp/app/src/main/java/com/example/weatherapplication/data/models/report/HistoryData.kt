package com.example.weatherapplication.data.models.report

import com.google.gson.annotations.SerializedName
import com.example.weatherapplication.data.models.report.HistoryWeatherContract.GsonName


data class HistoryData(

    @SerializedName(GsonName.TEMP)
    val temp: Field,

    @SerializedName(GsonName.PRESSURE)
    val pressure: Field,

    @SerializedName(GsonName.HUMIDITY)
    val humidity: Field,

    @SerializedName(GsonName.WIND)
    var wind: Field,

    @SerializedName(GsonName.PRECIPITATION)
    val precipitation: Field,

    )
