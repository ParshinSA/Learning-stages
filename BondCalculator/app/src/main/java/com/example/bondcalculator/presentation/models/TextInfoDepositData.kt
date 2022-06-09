package com.example.bondcalculator.presentation.models

data class TextInfoDepositData(
    val startBalance: Double,
    val endBalance: Double,
    val resultYield: Double,
    val term: Int,
    val profit: Double
)
