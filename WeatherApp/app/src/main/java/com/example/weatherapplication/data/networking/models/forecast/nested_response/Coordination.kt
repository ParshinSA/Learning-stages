package com.example.weatherapplication.data.networking.models.forecast.nested_response

import com.google.gson.annotations.SerializedName

data class Coordination(

    @SerializedName("lat")
    val latitude: Double,

    @SerializedName("lon")
    val longitude: Double
)