package com.example.weatherapplication.presentation.models.forecast.short

data class UiShortForecastDto(
    val cityName: String,
    val temperature: String,
    val iconId: String,
    val latitude: Double,
    val longitude: Double
)