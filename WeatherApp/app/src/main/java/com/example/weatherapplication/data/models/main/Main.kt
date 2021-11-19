package com.example.weatherapplication.data.models.main

import android.os.Parcelable
import androidx.room.PrimaryKey
import com.example.weatherapplication.data.models.main.MainContract.GsonName
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
