package com.example.bondcalculator.presentation.models

data class PurchaseHistoryItemRv(
    val year: String,
    val percentTotal: Float,
    val percentPreviousYearPayment: Float,
    val percentSumPayments: Float
)