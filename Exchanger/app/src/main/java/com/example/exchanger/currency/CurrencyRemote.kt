package com.example.exchanger.currency

import com.google.gson.annotations.SerializedName

data class CurrencyRemote(
    @SerializedName("CharCode")
    val type: String,
    @SerializedName("Value")
    val course: Double
)