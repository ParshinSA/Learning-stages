package com.example.bondcalculator.domain.models.portfplio

import com.example.bondcalculator.domain.models.bonds_data.DomainBondAndCalendar

data class DomainPortfolioYield(
    val startBalance: Double,
    val resultBalance: Double,
    val purchaseHistory: Map<DomainBondAndCalendar, Int>
)
