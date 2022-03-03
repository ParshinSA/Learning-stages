package com.example.weatherapplication.data.models.forecast.wind

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.example.weatherapplication.data.models.forecast.wind.WindContract.GsonName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Wind(

    @SerializedName(GsonName.SPEED)
    val speed: Double,

    @SerializedName(GsonName.ROUTE_DEGREES)
    val routeDegrees: Int
) : Parcelable

