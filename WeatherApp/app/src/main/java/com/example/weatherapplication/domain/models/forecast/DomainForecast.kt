package com.example.weatherapplication.domain.models.forecast

data class DomainForecast(

    val latitude: Double,
    val longitude: Double,
    val temperature: Double,
    val pressure: Int,
    val humidity: Int,
    val description: String,
    val iconId: String,
    val visibility: Long,
    val windSpeed: Double,
    val windDirectionDegrees: Float,
    val cityName: String,
    val country: String,
    val sunrise: Long,
    val sunset: Long,
    val forecastTime: Long

)