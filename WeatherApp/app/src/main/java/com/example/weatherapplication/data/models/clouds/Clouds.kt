package com.example.weatherapplication.data.models.clouds

import android.os.Parcelable
import com.example.weatherapplication.data.models.clouds.CloudsContract.GsonName
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Clouds(
    @SerializedName(GsonName.QUANTITY)
    val quantity: Int
) : Parcelable
