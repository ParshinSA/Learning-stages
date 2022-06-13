package com.example.bondcalculator.data.networking.models.exchange_rate.nested_response

import com.google.gson.annotations.SerializedName

data class ValuteData(
    @SerializedName("USD")
    val currentValute: CurrentValute
)
