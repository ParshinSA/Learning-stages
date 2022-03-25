package com.example.weatherapplication.data.networking.models.forecast.response.nesteds

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Weather(

//    @SerializedName("id")
//    val idWeather: Int,

    @SerializedName("description")
    val description: String,

    @SerializedName("icon")
    val iconId: String
) : Parcelable
