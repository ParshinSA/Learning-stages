package com.example.weatherapplication.presentation.models.forecast

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UiDetailsForecast(
    val latitude: Double,
    val longitude: Double,
    val temperature: String,
    val pressure: Int,
    val humidity: Int,
    val description: String,
    val iconId: String,
    val visibility: Long,
    val windSpeed: Int,
    val windDirectionDegrees: Float,
    val cityName: String,
    val country: String,
    val sunrise: String,
    val sunset: String,
    val forecastTime: String
) : Parcelable