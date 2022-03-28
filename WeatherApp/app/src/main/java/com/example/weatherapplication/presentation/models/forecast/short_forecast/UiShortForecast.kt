package com.example.weatherapplication.presentation.models.forecast.short_forecast

data class UiShortForecast(
    val cityName: String,
    val temperature: Double,
    val latitude: Double,
    val longitude: Double,
    val iconId: String
)
