package com.example.weatherapplication.data.models.forecast.coord

import android.os.Parcelable
import com.example.weatherapplication.data.models.forecast.coord.CoordinationContract.GsonName
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Coordination(

    @SerializedName(GsonName.LATITUDE)
    val lat: Double,

    @SerializedName(GsonName.LONGITUDE)
    val lon: Double
) : Parcelable