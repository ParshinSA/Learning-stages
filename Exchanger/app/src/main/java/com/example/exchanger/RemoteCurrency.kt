package com.example.exchanger

data class RemoteCurrency(
    val type: String,
    val course: Double,
    val logotype: Int = get(type) ?: R.drawable.ic_do_not
) {

    companion object {
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
}