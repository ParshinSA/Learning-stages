package com.example.bondcalculator.data.networking.models.bonds_data.nested_response

import com.google.gson.annotations.SerializedName

data class BondData(
    @SerializedName(value = "columns")
    val listParameterName: List<String>,
    @SerializedName(value = "data")
    val listParameterData: List<List<Any?>>
)