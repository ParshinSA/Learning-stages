package com.example.weatherapplication.data.networking.models.forecast.response.nesteds

import com.google.gson.annotations.SerializedName

data class Sys(

    @SerializedName("country")
    val country: String,

    @SerializedName("sunrise")
    val sunrise: Long,

    @SerializedName("sunset")
    val sunset: Long
)