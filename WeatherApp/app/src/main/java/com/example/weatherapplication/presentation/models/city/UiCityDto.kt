package com.example.weatherapplication.presentation.models.city

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UiCityDto(
    val latitude: Double,
    val longitude: Double,
    val cityName: String,
    val country: String
) : Parcelable
