package com.example.exchanger.currency

import com.example.exchanger.currency.AllValute
import com.google.gson.annotations.SerializedName

data class ObjectValute(
    @SerializedName("Valute")
    val valute: AllValute
)


