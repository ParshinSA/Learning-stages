package com.example.weatherapplication.data.networking.models.forecast.response

import com.example.weatherapplication.data.networking.models.forecast.response.nesteds.*
import com.google.gson.annotations.SerializedName

data class RemoteResponseForecastDto(

    @SerializedName("coord")
    val coordination: Coordination,

    @SerializedName("main")
    val main: Main,

    @SerializedName("weather")
    val weather: List<Weather>,

    @SerializedName("visibility")
    val visibility: Long,

    @SerializedName("wind")
    val wind: Wind,

    @SerializedName("name")
    val cityName: String,

    @SerializedName("sys")
    val sys: Sys,

    @SerializedName("dt")
    val forecastTime: Long
)
