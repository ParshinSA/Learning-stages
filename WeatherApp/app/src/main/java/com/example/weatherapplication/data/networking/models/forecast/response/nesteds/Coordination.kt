package com.example.weatherapplication.data.networking.models.forecast.response.nesteds

import com.google.gson.annotations.SerializedName

data class Coordination(

    @SerializedName("lat")
    val latitude: Double,

    @SerializedName("lon")
    val longitude: Double
)