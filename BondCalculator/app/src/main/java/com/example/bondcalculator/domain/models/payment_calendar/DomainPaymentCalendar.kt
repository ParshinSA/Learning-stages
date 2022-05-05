package com.example.bondcalculator.domain.models.payment_calendar

data class DomainPaymentCalendar(
    val amortizationPayment: HashMap<Long, Double>,
    val couponPayment: HashMap<Long, Double>
)
