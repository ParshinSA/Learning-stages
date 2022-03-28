package com.example.weatherapplication.data.networking.models.forecast.response.nesteds

import com.google.gson.annotations.SerializedName

data class Wind(

    @SerializedName("speed")
    val speed: Double,

    @SerializedName("deg")
    val routeDegrees: Float
)

