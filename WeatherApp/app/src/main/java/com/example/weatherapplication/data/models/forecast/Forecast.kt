package com.example.weatherapplication.data.models.forecast

import android.os.Parcelable
import com.example.weatherapplication.data.models.clouds.Clouds
import com.example.weatherapplication.data.models.main.Main
import com.example.weatherapplication.data.models.sys.Sys
import com.example.weatherapplication.data.models.weather.Weather
import com.example.weatherapplication.data.models.wind.Wind
import com.example.weatherapplication.data.models.forecast.WeatherForecastContract.GsonName
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Forecast(
    @SerializedName(GsonName.CITY_ID)
    val cityId: Int,

    @SerializedName(GsonName.MAIN)
    val main: Main,

    @SerializedName(GsonName.WEATHER)
    val weather: List<Weather>,

    @SerializedName(GsonName.VISIBILITY)
    val visibility: Long,

    @SerializedName(GsonName.WIND)
    val wind: Wind,

    @SerializedName(GsonName.CLOUDS)
    val clouds: Clouds,

    @SerializedName(GsonName.CITY_NAME)
    val cityName: String,

    @SerializedName(GsonName.SYS)
    val sys: Sys,

    @SerializedName(GsonName.TIME_FORECAST)
    val timeForecast: Long,

    @SerializedName(GsonName.STATUS_CODE)
    val statusCode: Int
) : Parcelable
