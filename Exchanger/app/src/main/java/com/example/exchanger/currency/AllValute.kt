package com.example.exchanger.currency

import com.google.gson.annotations.SerializedName

data class AllValute(

    @SerializedName("USD")
    val usd: CurrencyRemote,

    @SerializedName("EUR")
    val eur: CurrencyRemote,

    @SerializedName("GBP")
    val gbp: CurrencyRemote
)
