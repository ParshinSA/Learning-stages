package com.example.bondcalculator.data.networking.models.payment_calendar.nested_response

import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName(value = "columns")
    val listParameterName: List<String>,
    @SerializedName(value = "data")
    val listParameterData: List<List<Any?>>
)
