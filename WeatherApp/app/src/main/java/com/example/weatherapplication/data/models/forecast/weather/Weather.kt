package com.example.weatherapplication.data.models.forecast.weather

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.example.weatherapplication.data.models.forecast.weather.WeatherContract.GsonName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Weather(

    @SerializedName(GsonName.WEATHER_ID)
    val idWeather: Int,

    @SerializedName(GsonName.DESCRIPTION)
    val description: String,

    @SerializedName(GsonName.ICON_ID)
    val iconId: String
) : Parcelable
