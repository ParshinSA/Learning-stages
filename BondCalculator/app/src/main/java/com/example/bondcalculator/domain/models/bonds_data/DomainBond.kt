package com.example.bondcalculator.domain.models.bonds_data

data class DomainBond(
    val secId: String,
    val shortName: String, // наименование облигации
    val yieldPercent: Double, // доходность облигации в %
    val couponPercent: Double, // размер купона в % от номинала
    val nextDateCouponPayment: Long, // дата следующей выплаты купона
    val accumulatedCoupon: Double,  // текущий накопленный купонный доход
    val pricePercent: Double, // цена в % от номинала
    val lotSize: Int, // кол-во шт в одном лоте
    val nominal: Double, // номинальная стоимость в валюте
    val repayment: Long, // дата погашения (выплата номинал и последнего купона)
    val couponPeriod: Int, // периодичность купонных выплат
)
