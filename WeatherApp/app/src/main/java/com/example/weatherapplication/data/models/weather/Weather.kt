package com.example.weatherapplication.data.models.weather

import android.os.Parcelable
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.example.weatherapplication.data.models.weather.WeatherContract.GsonName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Weather(

    @SerializedName(GsonName.WEATHER_ID)
    val idWeather: Long,

    @SerializedName(GsonName.DESCRIPTION)
    val description: String,

    @SerializedName(GsonName.ICON_ID)
    val sectionURL: String
) : Parcelable