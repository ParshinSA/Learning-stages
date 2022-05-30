package com.example.bondcalculator.domain.models.payment_calendar

import java.util.*

data class DomainPaymentCalendar(
    val amortizationPayment: TreeMap<Long, Double>,
    val couponPayment: TreeMap<Long, Double>
)
