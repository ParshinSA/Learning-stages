package com.example.weatherapplication.data.networking.models.report.response.nesteds

import com.google.gson.annotations.SerializedName


data class ReportData(

    @SerializedName("temp")
    val temperature: FieldValue,

    @SerializedName("pressure")
    val pressure: FieldValue,

    @SerializedName("humidity")
    val humidity: FieldValue,

    @SerializedName("wind")
    var wind: FieldValue,

    @SerializedName("precipitation")
    val precipitation: FieldValue
)
