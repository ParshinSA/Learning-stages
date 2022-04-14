package com.example.bondcalculator.data.networking.models

import com.google.gson.annotations.SerializedName

data class RemoteResponseSecuritiesDataDto(
    @SerializedName("securities")
    val data: SecuritiesData
)