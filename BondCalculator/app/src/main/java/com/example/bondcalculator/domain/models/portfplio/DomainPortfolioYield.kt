package com.example.bondcalculator.domain.models.portfplio

import com.example.bondcalculator.domain.models.bonds_data.DomainBondAndCalendar
import java.util.*

data class DomainPortfolioYield(
    val startDateCalculate: Long,
    val endDateCalculate: Long,
    val startBalance: Double,
    val resultBalance: Double,
    val purchaseHistory: Map<DomainBondAndCalendar, Int>,
    val buyHistory: Map<Long, Double>,
    val generalPaymentList: Map<Long, Double>
)