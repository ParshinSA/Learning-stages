package com.example.weatherapplication.data.networking.models.forecast.nested_response

import com.google.gson.annotations.SerializedName

data class Wind(

    @SerializedName("speed")
    val speed: Double,

    @SerializedName("deg")
    val routeDegrees: Float
)

