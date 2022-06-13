package com.example.bondcalculator.domain.models.bonds_data

import com.example.bondcalculator.domain.models.payment_calendar.DomainPaymentCalendar

data class DomainBondAndCalendar(
    val secId: String,
    val nominal: Double,
    val shortName: String, // наименование облигации
    val couponValuePercent: Double, // размер купона в % от номинала
    val pricePercent: Double, // цена в % от номинала
    val lotSize: Int, // кол-во шт в одном лоте
    val repayment: Long, // дата погашения (выплата номинал и последнего купона)
    val couponPeriod: Int, // периодичность купонных выплат
    val paymentCalendar: DomainPaymentCalendar, // запланированный календарь выплат
)
