package com.example.weatherapplication.data.networking.models.report.response.nesteds

import com.google.gson.annotations.SerializedName


data class FieldValue(
    @SerializedName("median")
    val medianValue: Double
)
