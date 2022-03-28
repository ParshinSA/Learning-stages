package com.example.weatherapplication.presentation.models.city

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UiCity(
    val latitude: Double,
    val longitude: Double,
    val cityName: String,
    val country: String
) : Parcelable
