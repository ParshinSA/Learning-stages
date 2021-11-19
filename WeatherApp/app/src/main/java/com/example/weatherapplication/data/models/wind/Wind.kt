package com.example.weatherapplication.data.models.wind

import android.os.Parcelable
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.example.weatherapplication.data.models.wind.WindContract.GsonName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Wind(

    @SerializedName(GsonName.SPEED)
    val speed: Float,

    @SerializedName(GsonName.ROUTE_DEGREES)
    val routeDegrees: Int
) : Parcelable

