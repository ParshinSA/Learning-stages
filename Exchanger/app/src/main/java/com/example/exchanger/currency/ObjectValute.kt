package com.example.myapplication.currency

import com.google.gson.annotations.SerializedName

data class ObjectValute(
    @SerializedName("Valute")
    val valute: AllValute
)


