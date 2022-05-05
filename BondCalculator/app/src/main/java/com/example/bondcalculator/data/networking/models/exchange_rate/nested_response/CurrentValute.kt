package com.example.bondcalculator.data.networking.models.exchange_rate.nested_response

import com.google.gson.annotations.SerializedName

data class CurrentValute(
    @SerializedName("CharCode")
    val name: String,
    @SerializedName("Value")
    val value: Double
)