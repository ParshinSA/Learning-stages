package com.example.bondcalculator.domain.models.portfplio

import com.example.bondcalculator.domain.models.bonds_data.DomainBondAndCalendar

data class DomainPortfolioYield(
    val startDateCalculate: Long,
    val endDateCalculate: Long,
    val startBalance: Double,
    val resultBalance: Double,
    val purchaseHistory: Map<DomainBondAndCalendar, Int>,
    val generalPaymentList: Map<Long, Double>,
    val term: Int
)