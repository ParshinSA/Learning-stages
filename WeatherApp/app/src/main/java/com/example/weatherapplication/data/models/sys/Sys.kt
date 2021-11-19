package com.example.weatherapplication.data.models.sys

import android.os.Parcelable
import androidx.room.PrimaryKey
import com.example.weatherapplication.data.models.sys.SysContract.GsonName
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sys(

    @SerializedName(GsonName.COUNTRY)
    val country: String,

    @SerializedName(GsonName.SUNRISE)
    val sunrise: Long,

    @SerializedName(GsonName.SUNSET)
    val sunset: Long
) : Parcelable