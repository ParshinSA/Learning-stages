package com.example.bondcalculator.domain.models.calculate_yield_2

import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioSettings
import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioYield
import io.reactivex.Observable

interface CalculateYield2 {
    fun execute(settings: DomainPortfolioSettings): Observable<DomainPortfolioYield>
}