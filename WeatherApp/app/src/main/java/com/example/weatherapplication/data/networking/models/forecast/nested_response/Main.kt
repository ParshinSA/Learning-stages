package com.example.weatherapplication.data.networking.models.forecast.nested_response

import com.google.gson.annotations.SerializedName

data class Main(

    @SerializedName("temp")
    val temperature: Double,

    @SerializedName("pressure")
    val pressure: Int,

    @SerializedName("humidity")
    val humidity: Int
)