package com.example.bondcalculator.data.memory

import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioYield

interface SharedPreferenceDataSource {
    fun saveCalculate(calculate: DomainPortfolioYield)
    fun getCalculate(): DomainPortfolioYield
}
