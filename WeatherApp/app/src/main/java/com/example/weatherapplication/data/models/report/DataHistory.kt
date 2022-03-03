package com.example.weatherapplication.data.models.report

import com.google.gson.annotations.SerializedName
import com.example.weatherapplication.data.models.report.HistoryWeatherContract.GsonName


data class DataHistory(

    @SerializedName(GsonName.TEMPERATURE)
    val temperature: FieldValue,

    @SerializedName(GsonName.PRESSURE)
    val pressure: FieldValue,

    @SerializedName(GsonName.HUMIDITY)
    val humidity: FieldValue,

    @SerializedName(GsonName.WIND)
    var wind: FieldValue,

    @SerializedName(GsonName.PRECIPITATION)
    val precipitation: FieldValue
    )
