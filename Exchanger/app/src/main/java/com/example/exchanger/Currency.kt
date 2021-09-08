package com.example.exchanger

import androidx.annotation.DrawableRes

// Базовая валюта RUB

enum class Currency(val courseToRub: Double, @DrawableRes val icon: Int) {
    USD(73.2781, R.drawable.ic_usd),
    RUB(1.0, R.drawable.ic_rub),
    EUR(86.666, R.drawable.ic_eur),
    GBP(100.9479, R.drawable.ic_gbp);
}

data class Currency22(
    val name: String,
    val courseToRub: Double,
    val icon: Int = GetIcon().get(name) ?: R.drawable.ic_do_not
)

class GetIcon {
    private val mapIcon: Map<String, Int> = mapOf(
        "USD" to R.drawable.ic_usd,
        "RUB" to R.drawable.ic_rub,
        "EUR" to R.drawable.ic_eur,
        "GBP" to R.drawable.ic_gbp
    )

   fun get(name: String): Int? {
        return mapIcon[name]
    }
}
