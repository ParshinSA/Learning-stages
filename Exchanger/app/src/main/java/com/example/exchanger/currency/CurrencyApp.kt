package com.example.exchanger.currency

import androidx.annotation.DrawableRes
import com.example.exchanger.R

enum class CurrencyApp(
    @DrawableRes
    val logotype: Int
) {
    RUB(logotype = R.drawable.ic_rub),
    EUR(logotype = R.drawable.ic_eur),
    GBP(logotype = R.drawable.ic_gbp),
    USD(logotype = R.drawable.ic_usd)
}