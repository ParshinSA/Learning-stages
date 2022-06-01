package com.example.bondcalculator.presentation.models

data class PayoutsItem(
    val year: String,
    val sumYield: Double,
    val percentCouponYield: Float,
    val percentRedemptionYield: Float
)