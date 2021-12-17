package com.example.weatherapplication.data.models.forecast.main

import android.os.Parcelable
import com.example.weatherapplication.data.models.forecast.main.MainContract.GsonName
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Main(

    @SerializedName(GsonName.TEMP)
    val temp: Float,

    @SerializedName(GsonName.PRESSURE)
    val pressure: Int,

    @SerializedName(GsonName.HUMIDITY)
    val humidity: Int
) : Parcelable
