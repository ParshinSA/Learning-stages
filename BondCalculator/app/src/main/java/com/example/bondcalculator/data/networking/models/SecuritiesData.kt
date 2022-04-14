package com.example.bondcalculator.data.networking.models

import com.google.gson.annotations.SerializedName

data class SecuritiesData(
    @SerializedName(value = "columns")
    val listParameterName: List<String>,
    @SerializedName(value = "data")
    val listParameterData: List<List<Any?>>
)