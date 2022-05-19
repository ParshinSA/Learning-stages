package com.example.bondcalculator.data.networking.models.exchange_rate

import com.example.bondcalculator.data.networking.models.exchange_rate.nested_response.ValuteData
import com.google.gson.annotations.SerializedName

data class RemoteResponseExchangeRateDto(
    @SerializedName("Valute")
    val valuteData: ValuteData
)
