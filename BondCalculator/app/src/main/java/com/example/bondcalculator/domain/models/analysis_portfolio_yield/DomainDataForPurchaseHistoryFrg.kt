package com.example.bondcalculator.domain.models.analysis_portfolio_yield

data class DomainDataForPurchaseHistoryFrg(
    val allYearsInCalculatePeriod: Set<String>,
    val buyHistory: Map<Long, Double>,
    val paymentsHistory: Map<Long, Double>
    )