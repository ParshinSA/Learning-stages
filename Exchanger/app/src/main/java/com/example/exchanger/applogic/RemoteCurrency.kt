package com.example.exchanger.applogic

import com.example.exchanger.R

data class RemoteCurrency(
    val type: String,
    val course: Double,
    val logotype: Int = getLogotypeCurrency(type) ?: R.drawable.ic_do_not
) {

    companion object {
        private val listLogotypeCurrency: Map<String, Int> = mapOf(
            "USD" to R.drawable.ic_usd,
            "RUB" to R.drawable.ic_rub,
            "EUR" to R.drawable.ic_eur,
            "GBP" to R.drawable.ic_gbp
        )

        fun getLogotypeCurrency(name: String): Int? {
            return listLogotypeCurrency[name]
        }
    }
}