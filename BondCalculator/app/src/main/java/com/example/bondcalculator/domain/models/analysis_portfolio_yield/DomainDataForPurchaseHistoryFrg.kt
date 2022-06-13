package com.example.bondcalculator.domain.models.analysis_portfolio_yield

import java.util.*

data class DomainDataForPurchaseHistoryFrg(
    val startBalance: Double,
    val allYearsInCalculatePeriod: Set<String>,
    val couponPaymentsStepYear: TreeMap<String, Double>,
    val amortizationPaymentsStepYear: TreeMap<String, Double>
)