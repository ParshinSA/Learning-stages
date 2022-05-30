package com.example.bondcalculator.domain.models.analysis_portfolio_yield

data class DomainDataForPayoutsFrg(
    val allYearsInCalculatePeriod: Set<String>,
    val couponPaymentsStepYear: Map<String, Double>,
    val amortizationPaymentsStepYear: Map<String, Double>
)