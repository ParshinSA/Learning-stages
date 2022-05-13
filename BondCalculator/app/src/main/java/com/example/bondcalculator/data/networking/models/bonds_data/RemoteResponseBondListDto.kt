package com.example.bondcalculator.data.networking.models.bonds_data

import com.example.bondcalculator.data.networking.models.bonds_data.nested_response.BondData
import com.google.gson.annotations.SerializedName

data class RemoteResponseBondListDto(
    @SerializedName("securities")
    val data: BondData
)