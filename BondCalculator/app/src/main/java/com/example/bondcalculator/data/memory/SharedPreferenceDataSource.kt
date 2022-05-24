package com.example.bondcalculator.data.memory

import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioYield
import io.reactivex.Observable

interface SharedPreferenceDataSource {
    fun saveCalculatePortfolioYield(calculate: DomainPortfolioYield)
    fun getCalculatePortfolioYield(): Observable<DomainPortfolioYield>
}
